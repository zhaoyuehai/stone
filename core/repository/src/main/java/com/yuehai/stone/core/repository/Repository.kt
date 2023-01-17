package com.yuehai.stone.core.repository

import android.content.Context
import com.yuehai.stone.core.database.Database
import com.yuehai.stone.core.database.dao.CarDao
import com.yuehai.stone.core.datastore.DataStore
import com.yuehai.stone.core.model.TokenData
import com.yuehai.stone.core.network.NetworkDataSource
import com.yuehai.stone.core.network.RefreshTokenListener
import com.yuehai.stone.core.repository.impl.*

object Repository {
    private val networkDataSource = NetworkDataSource()
    lateinit var local: LocalRepository
    fun init(context: Context) {
        DataStore.init(context)
        Database.init(context)
        local = DefaultLocalRepository(
            DataStore.preferenceStore, DataStore.tokenStore
        ) {
            networkDataSource.setToken(it, object : RefreshTokenListener {
                override suspend fun onRefresh(token: TokenData?) {
                    DataStore.tokenStore.put(token)
                }
            })
        }
    }

    private fun carDao(): CarDao = Database.carDao()

    fun app(): AppRepository = DefaultAppRepository(networkDataSource)
    fun file(): FileRepository = DefaultFileRepository(networkDataSource)
    fun car(): CarRepository = OfflineFirstCarRepository(networkDataSource, carDao())
    fun user(): UserRepository = DefaultUserRepository(networkDataSource)
}