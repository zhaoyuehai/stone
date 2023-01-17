package com.yuehai.stone.core.repository.impl

import com.yuehai.stone.core.repository.AppRepository
import com.yuehai.stone.core.model.ResponseData
import com.yuehai.stone.core.model.VersionData
import com.yuehai.stone.core.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class DefaultAppRepository(private val dataSource: NetworkDataSource) :
    AppRepository {
    override suspend fun getAppVersion(): ResponseData<VersionData> =
        withContext(Dispatchers.IO) { dataSource.getAppVersion() }
}