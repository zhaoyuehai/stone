package com.yuehai.stone.core.database

import android.content.Context
import androidx.room.Room
import com.yuehai.stone.core.database.dao.CarDao

object Database {
    private lateinit var database: MyDatabase
    fun init(context: Context) {
        database = Room.databaseBuilder(context, MyDatabase::class.java, "stone_database")
            .fallbackToDestructiveMigration().build()
    }

    fun carDao(): CarDao = database.carDao()
}