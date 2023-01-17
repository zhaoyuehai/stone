package com.yuehai.stone.feature.login.index.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun LoginButtons(
    loginEnable: Boolean,
    onProcess: Boolean,
    loginByUserName: Boolean,
    onLoginTypeClick: () -> Unit,
    onRecoverClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        enabled = loginEnable,
        onClick = onLoginClick
    ) {
        Text(
            text = if (onProcess) "登录中..." else "登录",
            fontSize = 16.sp,
            color = if (onProcess) Color.Gray else Color.White
        )
        if (onProcess) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(22.dp)
                    .padding(4.dp),
                color = Color.Gray,
                strokeWidth = 2.dp
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            enabled = onProcess.not(),
            onClick = onLoginTypeClick,
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            Text(
                text = if (loginByUserName) "验证码登录" else "密码登录",
                fontWeight = FontWeight.W700,
                fontSize = 14.sp
            )
        }
        if (loginByUserName) {
            TextButton(
                enabled = onProcess.not(),
                onClick = onRecoverClick,
                contentPadding = PaddingValues(vertical = 4.dp),
            ) {
                Text(text = "忘记密码？", fontWeight = FontWeight.W700, fontSize = 14.sp)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLoginButtons() {
    Column {
        LoginButtons(loginEnable = true,
            onProcess = true,
            loginByUserName = false,
            onLoginClick = {},
            onLoginTypeClick = {},
            onRecoverClick = {})
    }
}