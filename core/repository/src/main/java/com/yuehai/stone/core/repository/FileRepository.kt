package com.yuehai.stone.core.repository

import com.yuehai.stone.core.model.ResponseData
import java.io.File

interface FileRepository {
    suspend fun download(
        url: String, filePath: String, onProgress: (Long, Long) -> Unit
    ): ResponseData<File>
}