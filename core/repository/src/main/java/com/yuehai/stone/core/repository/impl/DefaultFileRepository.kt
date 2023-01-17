package com.yuehai.stone.core.repository.impl

import com.yuehai.stone.core.repository.FileRepository
import com.yuehai.stone.core.model.ResponseData
import com.yuehai.stone.core.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

internal class DefaultFileRepository(private val dataSource: NetworkDataSource) : FileRepository {
    override suspend fun download(
        url: String, filePath: String, onProgress: (Long, Long) -> Unit
    ): ResponseData<File> =
        withContext(Dispatchers.IO) { dataSource.download(url, filePath, onProgress) }
}