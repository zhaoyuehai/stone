package com.yuehai.stone.feature.profile.index

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

internal class ProfileViewModel : ViewModel() {
    var uiState by mutableStateOf(ProfileUiState())
        private set
}