package com.yuehai.stone.feature.login.recover

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

internal class RecoverViewModel : ViewModel() {
    var uiState by mutableStateOf(RecoverUiState())
        private set
}