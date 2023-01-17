package com.yuehai.stone.feature.home.demo

import com.yuehai.stone.core.model.CarTypeData

internal data class DemoUiState(
    val title: String = "Demo",
    val isLoading: Boolean = true,
    val carTypeList: List<CarTypeData>? = null
)
