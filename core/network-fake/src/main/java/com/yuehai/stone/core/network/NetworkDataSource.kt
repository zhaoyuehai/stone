package com.yuehai.stone.core.network

import com.google.gson.Gson
import com.yuehai.stone.core.model.*
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.Language
import java.io.File

class NetworkDataSource : NetworkDataSourceApi {
    fun setToken(token: TokenData, listener: RefreshTokenListener) {}
    private val mGson = Gson()
    private fun <T> failure(message: String = "network-fake failure"): ResponseData<T> {
        return ResponseData.Failure(message)
    }

    private suspend fun <T> success(
        data: T, message: String = "network-fake success"
    ): ResponseData<T> {
        delay(1000)
        return ResponseData.Success(data, message)
    }

    @Language("JSON")
    val versionDataJson = """
        {
          "id": "2",
          "versionName": "0.0.2",
          "versionCode": 2,
          "miniVersionCode": 1,
          "desc": "1.新版本增加了...\r\n2.新版本优化了..." ,
          "downloadUrl": "https://poweraws.cnecloud.com/upgrade/pvpower.apk"
        }
        """.trimIndent()

    override suspend fun getAppVersion(): ResponseData<VersionData> =
        success(mGson.fromJson(versionDataJson, VersionData::class.java))


    override suspend fun sendPhoneCode(phoneNumber: String): ResponseData<Boolean> =
        success(true, "验证码已发送")

    @Language("JSON")
    private val tokenDataJson = """
        {
          "access_token": "7be7e230-9782-4405-8039-c678e4d31e15",
          "token_type": "Bearer",
          "refresh_token": "7be7e230-9782-4405-8039-c678e4d31e15",
          "expires_in": 604800,
          "scope": "all"
        }
        """.trimIndent()

    override suspend fun login(params: LoginParams): ResponseData<TokenData> =
        success(mGson.fromJson(tokenDataJson, TokenData::class.java), "登录成功")

    private val carTypes = listOf(
        CarTypeData(1, "坦克300越野车"),
        CarTypeData(2, "坦克300越野车"),
        CarTypeData(3, "坦克300越野车"),
        CarTypeData(4, "坦克300越野车"),
        CarTypeData(5, "坦克300越野车")
    )

    override suspend fun getCarTypes(): ResponseData<List<CarTypeData>> = success(carTypes)
    private val carInfo = listOf(
        CarInfoData(id = 1, carType = 1, name = "刚性矿卡A", number = "晋M·11111"),
        CarInfoData(id = 2, carType = 1, name = "刚性矿卡B", number = "晋M·22222"),
        CarInfoData(id = 3, carType = 1, name = "刚性矿卡C", number = "晋M·33333"),
        CarInfoData(id = 4, carType = 1, name = "刚性矿卡D", number = "晋M·44444"),
        CarInfoData(id = 5, carType = 1, name = "刚性矿卡E", number = "晋M·55555"),
        CarInfoData(id = 6, carType = 1, name = "刚性矿卡F", number = "晋M·66666"),
        CarInfoData(id = 7, carType = 1, name = "刚性矿卡G", number = "晋M·77777"),
        CarInfoData(id = 8, carType = 1, name = "刚性矿卡H", number = "晋M·88888"),
        CarInfoData(id = 9, carType = 1, name = "刚性矿卡I", number = "晋M·99999"),
        CarInfoData(id = 10, carType = 1, name = "刚性矿卡J", number = "冀F·1N7N9")
    )

    override suspend fun getCarInfo(): ResponseData<List<CarInfoData>> = success(carInfo)
    override suspend fun download(
        url: String, filePath: String, onProgress: (Long, Long) -> Unit
    ): ResponseData<File> = failure()
}