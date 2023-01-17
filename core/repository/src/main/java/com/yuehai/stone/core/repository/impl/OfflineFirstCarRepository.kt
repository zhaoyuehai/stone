package com.yuehai.stone.core.repository.impl

import com.yuehai.stone.core.repository.CarRepository
import com.yuehai.stone.core.database.dao.CarDao
import com.yuehai.stone.core.database.entity.CarInfoEntity
import com.yuehai.stone.core.database.entity.CarTypeEntity
import com.yuehai.stone.core.model.CarInfoData
import com.yuehai.stone.core.model.CarTypeData
import com.yuehai.stone.core.model.ResponseData
import com.yuehai.stone.core.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class OfflineFirstCarRepository(
    private val network: NetworkDataSource, private val dao: CarDao
) : CarRepository {
    override suspend fun getCarTypesStream(): Flow<ResponseData<List<CarTypeData>>> = flow {
        val carTypeList = dao.getCarTypes().map { item -> CarTypeData(id = item.id, item.name) }
        if (carTypeList.isNotEmpty()) emit(ResponseData.Success(carTypeList))
        val result = network.getCarTypes()
        if (result is ResponseData.Success) {
            result.data.forEach { dao.insertCarType(CarTypeEntity(it.id, it.name)) }
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    override suspend fun getCarInfoStream(): Flow<ResponseData<List<CarInfoData>>> = flow {
        val carInfo = dao.getCarInfo()
            .map { item -> CarInfoData(item.id, item.carType, item.name, item.number) }
        if (carInfo.isNotEmpty()) emit(ResponseData.Success(carInfo))
        val result = network.getCarInfo()
        if (result is ResponseData.Success) {
            result.data.forEach {
                dao.insertCarInfo(CarInfoEntity(it.id, it.name, it.carType, it.number))
            }
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
}