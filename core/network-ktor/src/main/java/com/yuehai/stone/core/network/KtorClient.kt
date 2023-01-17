package com.yuehai.stone.core.network

import android.util.Log
import com.yuehai.stone.core.model.HttpData
import com.yuehai.stone.core.model.ResponseData
import com.yuehai.stone.core.model.TokenData
import com.yuehai.stone.core.network.ktor.BuildConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.DateFormat
import java.util.concurrent.CancellationException

internal class KtorClient {
    private val client = HttpClient(OkHttp) { installCommon() }
    private lateinit var authorizedClient: HttpClient
    fun setToken(token: TokenData, listener: RefreshTokenListener) {
        authorizedClient = HttpClient(OkHttp) {
            installCommon()
            install(Auth) {
                bearer {
                    loadTokens { BearerTokens(token.access_token, token.refresh_token) }
                    refreshTokens {
                        try {
                            val result: TokenData = client.submitForm(url = buildString {
                                append(BuildConfig.BASE_URL)
                                append("/api/v1/user/refresh")//TODO
                            }, formParameters = Parameters.build {
                                append("refreshToken", token.refresh_token)
                            }) { markAsRefreshTokenRequest() }.body()
                            Log.e("---", "---refreshToken:$result")
                            listener.onRefresh(token)
                            BearerTokens(result.access_token, result.refresh_token)
                        } catch (e: Exception) {
                            Log.e("---", "---refreshToken:$e")
                            //TODO重新登录
                            listener.onRefresh(null)
                            null
                        }
                    }
                }
            }
        }
    }

    private fun HttpClientConfig<*>.installCommon() {
        defaultRequest { url(BuildConfig.BASE_URL) }
        if (BuildConfig.DEBUG) {
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("ktor-logger", message)
                    }
                }
            }
        }
        install(ContentNegotiation) {
            gson {
                setDateFormat(DateFormat.LONG)
                setPrettyPrinting()
            }
        }
    }

    private fun <T> resultData(httpData: HttpData<T>): ResponseData<T> = if (httpData.code == 200) {
        ResponseData.Success(httpData.data!!, httpData.message)
    } else {
        ResponseData.Failure(httpData.message)
    }

    private fun <T> resultData(exception: Exception): ResponseData<T> = ResponseData.Failure(
        when (exception) {
            is CancellationException -> "请求已取消"
            is HttpRequestTimeoutException -> "请求超时"
            is ConnectTimeoutException -> "连接超时"
            is SocketTimeoutException -> "连接超时"
            is ConnectException -> "网络不可用"
            is ResponseException -> "请求失败"
            else -> "未知异常"
        }
    )

    suspend inline fun <reified T> request(
        url: String, method: HttpMethod, params: Any? = null, authorized: Boolean = true
    ): ResponseData<T> = try {
        val httpData: HttpData<T> = (if (authorized) authorizedClient else client).request(url) {
            this.method = method
            contentType(ContentType.Application.Json)
            if (params != null) setBody(params)
        }.body()
        resultData(httpData)
    } catch (exception: Exception) {
        resultData(exception)
    }

    suspend inline fun <reified T> get(url: String, authorized: Boolean = true): ResponseData<T> =
        request(url, HttpMethod.Get, null, authorized)

    suspend inline fun <reified T> post(
        url: String, params: Any? = null, authorized: Boolean = true
    ): ResponseData<T> = request(url, HttpMethod.Post, params, authorized)

    suspend fun downloadFile(
        url: String, filePath: String, onProgress: (Long, Long) -> Unit, authorized: Boolean = true
    ): ResponseData<File> = withContext(Dispatchers.IO) {
        try {
            var result: ResponseData<File>? = null
            val fileName = url.substring(url.lastIndexOf("/") + 1)
            File(filePath).apply { if (isDirectory.not()) mkdirs() }
            val destFile = File(filePath + File.separator + fileName)
            (if (authorized) authorizedClient else client).prepareGet(url).execute { response ->
                val contentLength = response.contentLength() ?: 0
                if (destFile.exists() && destFile.length() == contentLength) {
                    result = ResponseData.Success(destFile, "文件已存在")
                } else {
                    if (destFile.exists()) destFile.delete()//删除旧文件
                    var lastCurrentLength = 0L
                    var currentLength = 0L
                    val channel: ByteReadChannel = response.body()
                    while (channel.isClosedForRead.not()) {
                        val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                        while (packet.isNotEmpty) {
                            val bytes = packet.readBytes()
                            destFile.appendBytes(bytes)
                            currentLength += bytes.size
                            if (currentLength == contentLength || currentLength - lastCurrentLength >= contentLength / 100) {//每读取1%，回调进度条
                                onProgress.invoke(currentLength, contentLength)
                                lastCurrentLength = currentLength
                            }
                        }
                    }
                    result = ResponseData.Success(destFile, "下载成功")
                }
            }
            result ?: ResponseData.Failure("下载失败")
        } catch (exception: Exception) {
            resultData(exception)
        }
    }
}