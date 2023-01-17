package com.yuehai.stone.feature.home.index

import com.yuehai.stone.core.model.CarInfoData

internal data class HomeUiState(
    val isOnline: Boolean = false,
    val isLoading: Boolean = true,
    val carInfoList: List<CarInfoData> = emptyList()
)