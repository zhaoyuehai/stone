package com.yuehai.stone.core.network

import com.yuehai.stone.core.model.TokenData

interface RefreshTokenListener {
    suspend fun onRefresh(token: TokenData?)
}