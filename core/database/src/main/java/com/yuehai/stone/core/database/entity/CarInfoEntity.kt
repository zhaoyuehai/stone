package com.yuehai.stone.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_info")
data class CarInfoEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "car_type") val carType: Int,
    @ColumnInfo(name = "number") val number: String
)