package com.yuehai.stone.feature.login.index

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuehai.stone.core.model.LoginParams
import com.yuehai.stone.core.model.ResponseData
import com.yuehai.stone.core.model.TokenData
import com.yuehai.stone.core.repository.Repository
import com.yuehai.stone.feature.login.LoginPreferenceKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.regex.Pattern

internal class LoginViewModel : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set
    private val repository = Repository.user()

    init {
        viewModelScope.launch {
            val username = Repository.local.getStream(LoginPreferenceKeys.USERNAME, "").first()
            val phoneNumber =
                Repository.local.getStream(LoginPreferenceKeys.PHONE_NUMBER, "").first()
            uiState = uiState.copy(username = username, phoneNumber = phoneNumber)
        }
    }

    fun updatePhoneNumber(text: String) {
        uiState = uiState.copy(phoneNumber = text.trim())
    }

    fun updatePhoneCode(text: String) {
        uiState = uiState.copy(phoneCode = text.trim())
    }

    fun updateUsername(text: String) {
        uiState = uiState.copy(username = text.trim())
    }

    fun updatePassword(text: String) {
        uiState = uiState.copy(password = text.trim())
    }

    fun switchLoginType() {
        uiState = uiState.copy(loginByUserName = uiState.loginByUserName.not())
    }

    fun login() {
        viewModelScope.launch {
            if (uiState.loginByUserName) {
                if (uiState.username.length < 6 || uiState.username.length > 20) {
//                    showToast("用户名6~20位")
                    return@launch
                }
                if (uiState.password.length < 8 || uiState.password.length > 12) {
//                    showToast("密码8~12位")
                    return@launch
                }
            } else {
                if (checkPhoneNum(uiState.phoneNumber).not()) {
//                    showToast("手机号码格式错误")
                    return@launch
                }
            }
            uiState = uiState.copy(loginOnProcess = true)
            val params = if (uiState.loginByUserName) {
                Repository.local.put(LoginPreferenceKeys.USERNAME, uiState.username)
                LoginParams(username = uiState.username, password = uiState.password, type = "1")
            } else {
                Repository.local.put(LoginPreferenceKeys.PHONE_NUMBER, uiState.phoneNumber)
                LoginParams(phoneNumber = uiState.phoneNumber, phoneCode = uiState.phoneCode)
            }
            val result = repository.login(params)
            if (result is ResponseData.Success) {
//                showToast(result.message ?: "登录成功")
                Repository.local.putToken(
                    TokenData(
                        access_token = result.data.access_token,
                        token_type = result.data.token_type,
                        refresh_token = result.data.refresh_token,
                        expires_in = result.data.expires_in,
                        scope = result.data.scope
                    )
                )
            } else {
//                showToast(result.message ?: "登录失败")
                uiState = uiState.copy(loginOnProcess = false)
            }
        }
    }

    fun sendPhoneCode() {
        viewModelScope.launch {
            if (checkPhoneNum(uiState.phoneNumber).not()) {
//                showToast("手机号码格式错误")
                return@launch
            }
            uiState = uiState.copy(sendCodeOnProcess = true)
            val result = repository.sendPhoneCode(uiState.phoneNumber)
            uiState = uiState.copy(sendCodeOnProcess = false)
            if (result is ResponseData.Success) {
//                showToast(result.message ?: "验证码已发送")
                timerStart()
            } else {
//                showToast(result.message ?: "验证码发送失败")
            }
        }
    }

    private fun checkPhoneNum(phoneNumber: String) =
        Pattern.compile("^((13\\d)|(15[^4])|(18\\d)|(17[0-8])|(14[5-9])|(166)|(19[8,9])|)\\d{8}$")
            .matcher(phoneNumber).matches()

    private suspend fun timerStart() = flow {
        for (i in 60 downTo 0) {
            emit(i)
            delay(1000)
        }
    }.flowOn(Dispatchers.IO).collect { uiState = uiState.copy(countdownValue = it) }
}
