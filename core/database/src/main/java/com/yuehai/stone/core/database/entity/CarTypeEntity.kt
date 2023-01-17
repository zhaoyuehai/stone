package com.yuehai.stone.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_type")
data class CarTypeEntity(
    @PrimaryKey val id: Int, @ColumnInfo(name = "name") val name: String
)