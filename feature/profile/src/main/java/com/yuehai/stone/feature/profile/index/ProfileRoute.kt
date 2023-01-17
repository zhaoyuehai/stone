package com.yuehai.stone.feature.profile.index

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
internal fun ProfileRoute(viewModel: ProfileViewModel, navigateToSetting: () -> Unit) {
    ProfileScreen(uiState = viewModel.uiState, onSettingClick = navigateToSetting)
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier, uiState: ProfileUiState, onSettingClick: () -> Unit
) {
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(modifier = Modifier.fillMaxWidth(), title = { Text("Profile") }, actions = {
            IconButton(onClick = onSettingClick) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
            }
        })
    }) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .background(Color(0x32FF9800))
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(20.dp))
                AsyncImage(model = uiState.imageUrl, contentDescription = null)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewProfileScreen() {
    val uiState = ProfileUiState()
    ProfileScreen(uiState = uiState, onSettingClick = {})
}