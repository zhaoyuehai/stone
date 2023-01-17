package com.yuehai.stone.feature.profile.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuehai.stone.core.repository.Repository
import kotlinx.coroutines.launch

internal class SettingViewModel : ViewModel() {
    var uiState by mutableStateOf(SettingUiState())
        private set

    internal fun loginOut() {
        viewModelScope.launch {
            Repository.local.putToken(null)
        }
    }
}