package com.yuehai.stone.feature.login.index

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yuehai.stone.feature.login.index.LoginViewModel.*
import com.yuehai.stone.feature.login.index.component.LoginButtons
import com.yuehai.stone.feature.login.index.component.PhoneLogin
import com.yuehai.stone.feature.login.index.component.UserNameLogin
import com.yuehai.stone.core.ui.theme.BlueLight

@Composable
internal fun LoginRoute(viewModel: LoginViewModel, navigateToRecover: () -> Unit) {
    LoginScreen(
        onRecoverClick = navigateToRecover,
        uiState = viewModel.uiState,
        setUsername = viewModel::updateUsername,
        setPassword = viewModel::updatePassword,
        setPhoneNumber = viewModel::updatePhoneNumber,
        setPhoneCode = viewModel::updatePhoneCode,
        onSendCodeClick = viewModel::sendPhoneCode,
        onLoginTypeClick = viewModel::switchLoginType,
        onLoginClick = viewModel::login
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun LoginScreen(
    onRecoverClick: () -> Unit,
    uiState: LoginUiState,
    setPhoneNumber: (String) -> Unit,
    setPhoneCode: (String) -> Unit,
    setUsername: (String) -> Unit,
    setPassword: (String) -> Unit,
    onSendCodeClick: () -> Unit,
    onLoginTypeClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(BlueLight)
            .fillMaxSize()
            .padding(bottom = 150.dp)
    ) {
        Column(
            modifier = Modifier
                .width(260.dp)
                .align(Alignment.Center)
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            if (uiState.loginByUserName) {
                UserNameLogin(username = uiState.username,
                    password = uiState.password,
                    setUsername = setUsername,
                    setPassword = setPassword,
                    onLoginClick = {
                        keyboardController?.hide()
                        onLoginClick()
                    })
            } else {
                PhoneLogin(phoneNumber = uiState.phoneNumber,
                    phoneCode = uiState.phoneCode,
                    onProcess = uiState.sendCodeOnProcess,
                    countdownValue = uiState.countdownValue,
                    setPhoneNumber = setPhoneNumber,
                    setPhoneCode = setPhoneCode,
                    sendCodeEnable = uiState.sendCodeEnable,
                    onSendCodeClick = onSendCodeClick,
                    onLoginClick = {
                        keyboardController?.hide()
                        onLoginClick()
                    })
            }

            LoginButtons(loginEnable = uiState.loginEnable,
                onProcess = uiState.loginOnProcess,
                loginByUserName = uiState.loginByUserName,
                onLoginTypeClick = onLoginTypeClick,
                onRecoverClick = onRecoverClick,
                onLoginClick = {
                    keyboardController?.hide()
                    onLoginClick()
                })
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    LoginScreen(uiState = LoginUiState(
        loginByUserName = false,
        sendCodeOnProcess = false,
        countdownValue = 12,
        phoneNumber = "18511111111",
        phoneCode = "6666"
    ),
        onLoginClick = {},
        setPhoneNumber = {},
        setPhoneCode = {},
        setUsername = {},
        setPassword = {},
        onSendCodeClick = {},
        onLoginTypeClick = {},
        onRecoverClick = {})
}
