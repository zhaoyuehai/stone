package com.yuehai.stone.core.repository

import com.yuehai.stone.core.model.CarInfoData
import com.yuehai.stone.core.model.CarTypeData
import com.yuehai.stone.core.model.ResponseData
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    suspend fun getCarTypesStream(): Flow<ResponseData<List<CarTypeData>>>
    suspend fun getCarInfoStream(): Flow<ResponseData<List<CarInfoData>>>
}