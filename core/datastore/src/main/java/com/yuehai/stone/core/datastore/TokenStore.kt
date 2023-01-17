package com.yuehai.stone.core.datastore

import com.yuehai.stone.core.model.TokenData
import kotlinx.coroutines.flow.Flow

interface TokenStore {
    suspend fun put(token: TokenData?)
    fun getStream(): Flow<TokenData?>
}