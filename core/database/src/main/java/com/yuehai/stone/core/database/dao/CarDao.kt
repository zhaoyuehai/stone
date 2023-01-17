package com.yuehai.stone.core.database.dao

import androidx.room.*
import com.yuehai.stone.core.database.entity.CarInfoEntity
import com.yuehai.stone.core.database.entity.CarTypeEntity

//DAO代表数据访问对象，是一个提供数据访问的 Kotlin 类。
//具体而言，您会在 DAO 中包含用于读取和操作数据的函数。
// 对 DAO 调用函数相当于对数据库执行 SQL 命令。
@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCarInfo(carInfo: CarInfoEntity)

    @Update
    suspend fun updateCarInfo(carInfo: CarInfoEntity)

    @Query("SELECT * FROM car_info ORDER BY id ASC")
    suspend fun getCarInfo(): List<CarInfoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCarType(carType: CarTypeEntity)

    @Update
    suspend fun updateCarType(carType: CarTypeEntity)

    @Query("DELETE FROM car_type")
    suspend fun deleteCarType()

    @Query("SELECT * FROM car_type ORDER BY id ASC")
    suspend fun getCarTypes(): List<CarTypeEntity>
}