package com.yuehai.stone.core.network

import com.yuehai.stone.core.model.*
import com.yuehai.stone.core.model.VersionData
import java.io.File

interface NetworkDataSourceApi {
    suspend fun getAppVersion(): ResponseData<VersionData>
    suspend fun sendPhoneCode(phoneNumber: String): ResponseData<Boolean>
    suspend fun login(params: LoginParams): ResponseData<TokenData>
    suspend fun getCarTypes(): ResponseData<List<CarTypeData>>
    suspend fun getCarInfo(): ResponseData<List<CarInfoData>>
    suspend fun download(
        url: String, filePath: String, onProgress: (Long, Long) -> Unit
    ): ResponseData<File>
}