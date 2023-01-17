package com.yuehai.stone.core.network

import com.yuehai.stone.core.model.*
import java.io.File

class NetworkDataSource : NetworkDataSourceApi {
    private val client = KtorClient()
    fun setToken(token: TokenData, listener: RefreshTokenListener) {
        client.setToken(token, listener)
    }

    override suspend fun getAppVersion(): ResponseData<VersionData> =
        client.get("system/app/version", false)

    override suspend fun sendPhoneCode(phoneNumber: String): ResponseData<Boolean> =
        client.get("system/usermanage/getPhoneCode/$phoneNumber", false)

    override suspend fun login(params: LoginParams): ResponseData<TokenData> =
        client.post("auth/login", params, false)

    override suspend fun getCarTypes(): ResponseData<List<CarTypeData>> =
        client.get("system/car/getCarTypes")

    override suspend fun getCarInfo(): ResponseData<List<CarInfoData>> =
        client.get("system/car/getCarInfo")

    override suspend fun download(
        url: String, filePath: String, onProgress: (Long, Long) -> Unit
    ): ResponseData<File> = client.downloadFile(url, filePath, onProgress, false)
}