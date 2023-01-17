package com.yuehai.stone.app.full

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuehai.stone.core.model.VersionData
import com.yuehai.stone.core.repository.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    sealed class PageType {
        object Splash : PageType()
        object Login : PageType()
        object Main : PageType()
    }

    var pageType by mutableStateOf<PageType>(PageType.Splash)
        private set
    var versionData by mutableStateOf<VersionData?>(null)
        private set

    init {
        viewModelScope.launch {
            Repository.local.getTokenStream().collect {
                pageType = if (it == null) PageType.Login else PageType.Main
            }
        }
        checkUpgrade(false)
    }

    private var checkUpgradeJob: Job? = null
    fun checkUpgrade(showToast: Boolean = true) {
        if (checkUpgradeJob?.isActive == true) return
        checkUpgradeJob = viewModelScope.launch {
            val result = Repository.app().getAppVersion()
//            if (result is ResultData.Success && currentAppVersionCode < result.data.versionCode) {
            versionData = result.data
//            } else if (showToast) {
//                showToast(if (result is ResultData.Success) "已经是最新版本" else result.message ?: "请求失败")
//            }
        }
    }

    fun dismissUpgradeDialog() {
        versionData = null
    }
}