package com.yuehai.stone.core.repository

import com.yuehai.stone.core.model.TokenData
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun <T> put(key: String, value: T)
    fun <T> getStream(key: String, default: T): Flow<T>
    suspend fun putStringSet(key: String, value: Set<String>)
    fun stringSetStream(key: String): Flow<Set<String>>
    suspend fun putToken(token: TokenData?)
    fun getTokenStream(): Flow<TokenData?>
}