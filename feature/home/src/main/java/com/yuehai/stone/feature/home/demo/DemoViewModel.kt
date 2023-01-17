package com.yuehai.stone.feature.home.demo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuehai.stone.core.model.CarTypeData
import com.yuehai.stone.core.model.ResponseData
import com.yuehai.stone.core.repository.Repository
import kotlinx.coroutines.launch

internal class DemoViewModel : ViewModel() {
    private val repository = Repository.car()
    var uiState by mutableStateOf(DemoUiState())
        private set

    init {
        viewModelScope.launch {
            repository.getCarTypesStream().collect {
                if (it is ResponseData.Success) {
                    uiState = uiState.copy(isLoading = false, carTypeList = it.data)
                } else {
                    uiState = uiState.copy(isLoading = false)
                }
            }
        }
    }

    fun clickButton(item: CarTypeData) {}
}