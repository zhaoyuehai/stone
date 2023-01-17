package com.yuehai.stone.core.datastore

import android.content.Context

object DataStore {
    lateinit var preferenceStore: PreferenceStore
    lateinit var tokenStore: TokenStore
    fun init(context: Context) {
        preferenceStore = DefaultPreferenceStore(context)
        tokenStore = DefaultTokenStore(context)
    }
}