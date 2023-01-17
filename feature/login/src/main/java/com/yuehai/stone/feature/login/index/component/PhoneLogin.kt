package com.yuehai.stone.feature.login.index.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * @param countdownValue 发送验证码倒计时（0秒时可发送验证码）
 */
@Composable
internal fun PhoneLogin(
    phoneNumber: String,
    phoneCode: String,
    onProcess: Boolean,
    countdownValue: Int,
    setPhoneNumber: (String) -> Unit,
    setPhoneCode: (String) -> Unit,
    sendCodeEnable: Boolean,
    onSendCodeClick: () -> Unit,
    onLoginClick: KeyboardActionScope.() -> Unit
) {
    val phoneCodeFocus = remember { FocusRequester() }
    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        label = { Text("手机号") },
        placeholder = { Text(text = "请输入手机号") },
        value = phoneNumber,
        onValueChange = {
            setPhoneNumber(it)
        },
        singleLine = true,
        textStyle = TextStyle(color = Color.DarkGray, fontWeight = FontWeight.W700),
        leadingIcon = { Icon(imageVector = Icons.Filled.Phone, contentDescription = null) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { phoneCodeFocus.requestFocus() }),
        trailingIcon = {
            if (phoneNumber.isNotEmpty()) {
                Icon(imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .size(14.dp)
                        .clickable {
                            setPhoneNumber("")
                        })
            }
        })
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 12.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(3f)
                .focusRequester(phoneCodeFocus),
            value = phoneCode,
            onValueChange = {
                setPhoneCode(it)
            },
            singleLine = true,
            textStyle = TextStyle(color = Color.DarkGray, fontWeight = FontWeight.W700),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = if (phoneNumber.isNotEmpty() && phoneCode.isNotEmpty()) ImeAction.Go else ImeAction.None
            ),
            keyboardActions = KeyboardActions(onGo = onLoginClick),
            placeholder = { Text("验证码") },
        )
        Spacer(modifier = Modifier.width(4.dp))
        OutlinedButton(
            modifier = Modifier
                .weight(2f)
                .height(56.dp),
            contentPadding = PaddingValues(0.dp),
            enabled = sendCodeEnable,
            onClick = onSendCodeClick
        ) {
            if (onProcess) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(22.dp)
                        .padding(4.dp),
                    color = Color.Gray,
                    strokeWidth = 2.dp
                )
            } else {
                Text(text = if (countdownValue > 0) "${countdownValue}s后重试" else "发送验证码")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPhoneLogin() {
    Column {
        PhoneLogin(phoneNumber = "18511073583",
            phoneCode = "6666",
            onProcess = false,
            countdownValue = 10,
            setPhoneNumber = {},
            setPhoneCode = {},
            sendCodeEnable = false,
            onSendCodeClick = {},
            onLoginClick = {})
    }
}