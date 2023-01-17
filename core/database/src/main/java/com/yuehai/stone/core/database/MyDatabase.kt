package com.yuehai.stone.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yuehai.stone.core.database.dao.CarDao
import com.yuehai.stone.core.database.entity.CarInfoEntity
import com.yuehai.stone.core.database.entity.CarTypeEntity

@Database(
    entities = [CarInfoEntity::class, CarTypeEntity::class], version = 1,//每当您更改数据库表的架构时，都必须提升版本号。
    exportSchema = false//这样就不会保留架构版本记录的备份。
)
internal abstract class MyDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
}