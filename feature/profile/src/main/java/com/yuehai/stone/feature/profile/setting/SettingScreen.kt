package com.yuehai.stone.feature.profile.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yuehai.stone.core.ui.util.getVersionName

@Composable
internal fun SettingScreen(
    viewModel: SettingViewModel, checkUpgrade: () -> Unit, popBack: () -> Unit
) {
    SettingScreen(
        uiState = viewModel.uiState,
        onCheckUpgradeClick = checkUpgrade,
        onBackClick = popBack,
        onLoginOutClick = viewModel::loginOut
    )
}

@Composable
private fun SettingScreen(
    modifier: Modifier = Modifier,
    uiState: SettingUiState,
    onCheckUpgradeClick: () -> Unit,
    onBackClick: () -> Unit,
    onLoginOutClick: () -> Unit
) {
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(title = { Text(text = uiState.title) }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0x61E91E63)),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .defaultMinSize(minWidth = 200.dp, minHeight = 50.dp),
                    border = BorderStroke(1.dp, Color(0xFFFF5722)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF5722)),
                    onClick = onCheckUpgradeClick,
                ) {
                    Text(text = "检查更新", fontSize = 18.sp)
                    Text(text = "(v${LocalContext.current.getVersionName()})", fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(width = 200.dp, height = 50.dp), colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFE91E63)
                    ), onClick = onLoginOutClick
                ) {
                    Text(text = "退出登录", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}
