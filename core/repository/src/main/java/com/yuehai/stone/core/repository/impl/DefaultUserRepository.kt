package com.yuehai.stone.core.repository.impl

import com.yuehai.stone.core.repository.UserRepository
import com.yuehai.stone.core.model.LoginParams
import com.yuehai.stone.core.model.ResponseData
import com.yuehai.stone.core.model.TokenData
import com.yuehai.stone.core.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class DefaultUserRepository(private val dataSource: NetworkDataSource) :
    UserRepository {
    override suspend fun sendPhoneCode(phoneNumber: String): ResponseData<Boolean> =
        withContext(Dispatchers.IO) { dataSource.sendPhoneCode(phoneNumber) }

    override suspend fun login(params: LoginParams): ResponseData<TokenData> =
        withContext(Dispatchers.IO) { dataSource.login(params) }
}