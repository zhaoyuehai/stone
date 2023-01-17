package com.yuehai.stone.core.repository

import com.yuehai.stone.core.model.ResponseData
import com.yuehai.stone.core.model.VersionData

interface AppRepository {
    suspend fun getAppVersion(): ResponseData<VersionData>
}