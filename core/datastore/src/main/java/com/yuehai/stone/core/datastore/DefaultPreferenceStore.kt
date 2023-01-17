package com.yuehai.stone.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val PREFERENCES = "preferences"
private val Context.preferences by preferencesDataStore(name = PREFERENCES)

internal class DefaultPreferenceStore constructor(context: Context) : PreferenceStore {
    private val dataStore = context.preferences
    override suspend fun <T> put(key: String, value: T) {
        when (value) {
            is Int -> dataStore.edit { it[intPreferencesKey(key)] = value }
            is Double -> dataStore.edit { it[doublePreferencesKey(key)] = value }
            is String -> dataStore.edit { it[stringPreferencesKey(key)] = value }
            is Boolean -> dataStore.edit { it[booleanPreferencesKey(key)] = value }
            is Float -> dataStore.edit { it[floatPreferencesKey(key)] = value }
            is Long -> dataStore.edit { it[longPreferencesKey(key)] = value }
            else -> throw IllegalArgumentException("PreferenceStore unsupported!")
        }
    }

    override suspend fun putStringSet(key: String, value: Set<String>) {
        dataStore.edit { it[stringSetPreferencesKey(key)] = value }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> getStream(key: String, default: T): Flow<T> =
        dataStore.data.catch { if (it is IOException) emit(emptyPreferences()) else throw  it }
            .map {
                when (default) {
                    is Int -> it[intPreferencesKey(key)] ?: default
                    is Double -> it[doublePreferencesKey(key)] ?: default
                    is String -> it[stringPreferencesKey(key)] ?: default
                    is Boolean -> it[booleanPreferencesKey(key)] ?: default
                    is Float -> it[floatPreferencesKey(key)] ?: default
                    is Long -> it[longPreferencesKey(key)] ?: default
                    else -> throw IllegalArgumentException("PreferenceStore unsupported!")
                }
            } as Flow<T>

    override fun getStringSetStream(key: String): Flow<Set<String>> =
        dataStore.data.catch { if (it is IOException) emit(emptyPreferences()) else throw  it }
            .map { it[stringSetPreferencesKey(key)] ?: emptySet() }
}