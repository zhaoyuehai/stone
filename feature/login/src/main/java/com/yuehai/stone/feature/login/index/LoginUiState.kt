package com.yuehai.stone.feature.login.index

internal data class LoginUiState(
    val loginByUserName: Boolean = true,
    val username: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val phoneCode: String = "",
    val countdownValue: Int = 0,
    val sendCodeOnProcess: Boolean = false,
    val loginOnProcess: Boolean = false
)

internal val LoginUiState.sendCodeEnable: Boolean
    get() = phoneNumber.isNotEmpty() && sendCodeOnProcess.not() && countdownValue == 0
internal val LoginUiState.loginEnable: Boolean
    get() = loginOnProcess.not() && (if (loginByUserName) username.isNotEmpty() && password.isNotEmpty() else phoneNumber.isNotEmpty() && phoneCode.isNotEmpty())