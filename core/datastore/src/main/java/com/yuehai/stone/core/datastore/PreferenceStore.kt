package com.yuehai.stone.core.datastore

import kotlinx.coroutines.flow.Flow

interface PreferenceStore {
    suspend fun <T> put(key: String, value: T)
    suspend fun putStringSet(key: String, value: Set<String>)
    fun <T> getStream(key: String, default: T): Flow<T>
    fun getStringSetStream(key: String): Flow<Set<String>>
}