package com.yuehai.stone.feature.login.recover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun RecoverRoute(viewModel: RecoverViewModel, popBack: () -> Unit) {
    RecoverScreen(uiState = viewModel.uiState, onBackClick = popBack)
}

@Composable
private fun RecoverScreen(
    modifier: Modifier = Modifier, uiState: RecoverUiState, onBackClick: () -> Unit
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
                .background(Color(0x4C009688)),
            contentAlignment = Alignment.Center
        ) {
            Button(modifier = Modifier.size(width = 120.dp, height = 50.dp), onClick = {}) {
                Text(text = "找回密码")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRecoverScreen() {
    RecoverScreen(uiState = RecoverUiState(), onBackClick = {})
}
