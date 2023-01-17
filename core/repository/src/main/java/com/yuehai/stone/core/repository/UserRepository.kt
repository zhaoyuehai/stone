package com.yuehai.stone.core.repository

import com.yuehai.stone.core.model.LoginParams
import com.yuehai.stone.core.model.ResponseData
import com.yuehai.stone.core.model.TokenData

interface UserRepository {
    suspend fun sendPhoneCode(phoneNumber: String): ResponseData<Boolean>
    suspend fun login(params: LoginParams): ResponseData<TokenData>
}