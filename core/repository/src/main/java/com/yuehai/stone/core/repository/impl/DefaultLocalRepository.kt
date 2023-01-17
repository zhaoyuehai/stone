package com.yuehai.stone.core.repository.impl

import com.yuehai.stone.core.datastore.PreferenceStore
import com.yuehai.stone.core.datastore.TokenStore
import com.yuehai.stone.core.model.TokenData
import com.yuehai.stone.core.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DefaultLocalRepository(
    private val preferenceStore: PreferenceStore,
    private val tokenStore: TokenStore,
    private val setToken: (tokenData: TokenData) -> Unit
) : LocalRepository {
    override suspend fun <T> put(key: String, value: T) {
        preferenceStore.put(key, value)
    }

    override fun <T> getStream(key: String, default: T): Flow<T> =
        preferenceStore.getStream(key, default)

    override suspend fun putStringSet(key: String, value: Set<String>) {
        preferenceStore.putStringSet(key, value)
    }

    override fun stringSetStream(key: String): Flow<Set<String>> =
        preferenceStore.getStringSetStream(key)

    override suspend fun putToken(token: TokenData?) {
        tokenStore.put(token)
    }

    override fun getTokenStream(): Flow<TokenData?> = tokenStore.getStream().map {
        if (it != null) setToken(it)
        it
    }
}