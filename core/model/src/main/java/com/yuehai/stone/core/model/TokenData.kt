package com.yuehai.stone.core.model

data class TokenData(
    val access_token: String,
    val token_type: String,
    val refresh_token: String,
    val expires_in: Long,
    val scope: String
)