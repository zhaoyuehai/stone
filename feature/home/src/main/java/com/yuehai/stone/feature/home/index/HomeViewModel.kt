package com.yuehai.stone.feature.home.index

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuehai.stone.core.model.ResponseData
import com.yuehai.stone.core.repository.Repository
import kotlinx.coroutines.launch

internal class HomeViewModel : ViewModel() {
    private val repository = Repository.car()
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadData()
    }

    internal fun loadData() {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            repository.getCarInfoStream().collect {
                uiState = if (it is ResponseData.Success) {
                    uiState.copy(isLoading = false, carInfoList = it.data)
                } else {
                    uiState.copy(isLoading = false)
                }
            }
        }
    }
}