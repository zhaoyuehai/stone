package com.yuehai.stone.feature.login.index.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun UserNameLogin(
    username: String,
    password: String,
    setUsername: (String) -> Unit,
    setPassword: (String) -> Unit,
    onLoginClick: KeyboardActionScope.() -> Unit
) {
    val passwordVisible = remember {
        mutableStateOf(false)
    }
    val passwordFocus = remember { FocusRequester() }
    OutlinedTextField(label = { Text("用户名") },
        placeholder = { Text(text = "请输入用户名") },
        value = username,
        onValueChange = { setUsername(it) },
        singleLine = true,
        textStyle = TextStyle(color = Color.DarkGray, fontWeight = FontWeight.W700),
        leadingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = null) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() }),
        trailingIcon = {
            if (username.isNotEmpty()) {
                Icon(imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .size(14.dp)
                        .clickable {
                            setUsername("")
                        })
            }
        })
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(modifier = Modifier.focusRequester(passwordFocus),
        label = { Text("密码") },
        placeholder = { Text(text = "请输入密码") },
        value = password,
        onValueChange = { setPassword(it) },
        singleLine = true,
        textStyle = TextStyle(color = Color.DarkGray, fontWeight = FontWeight.W700),
        leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
        trailingIcon = {
            Icon(imageVector = if (passwordVisible.value) Icons.Filled.AddCircle else Icons.Filled.CheckCircle,
                contentDescription = null,
                modifier = Modifier
                    .size(14.dp)
                    .clickable {
                        passwordVisible.value = !passwordVisible.value
                    })
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = if (username.isNotEmpty() && password.isNotEmpty()) ImeAction.Go else ImeAction.None
        ),
        keyboardActions = KeyboardActions(onGo = onLoginClick),
        visualTransformation = (if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation())
    )
}

@Preview
@Composable
private fun PreviewUserNameLogin() {
    Column {
        UserNameLogin(username = "admin",
            password = "123456",
            setUsername = {},
            setPassword = {},
            onLoginClick = {})
    }
}