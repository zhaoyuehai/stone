package com.yuehai.stone.feature.home.index

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yuehai.stone.core.model.CarInfoData
import com.yuehai.stone.core.ui.component.PageOnError
import com.yuehai.stone.core.ui.component.PageOnLoading

@Composable
internal fun HomeScreen(viewModel: HomeViewModel, navigateToDemo: () -> Unit) {
    HomeScreen(
        uiState = viewModel.uiState,
        onDemoClick = navigateToDemo,
        onRetryClick = viewModel::loadData
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onDemoClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(modifier = Modifier.fillMaxWidth(), title = {
            Text("Hello${if (uiState.isOnline) "" else " (未连接)"}")
        }, actions = {
            IconButton(onClick = onDemoClick) {
                Icon(imageVector = Icons.Filled.Build, contentDescription = null)
            }
        })
    }) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> PageOnLoading()
                uiState.carInfoList.isEmpty() -> PageOnError(onRetryClick = onRetryClick)
                else -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    state = rememberLazyListState(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(uiState.carInfoList.size) { index ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Card {
                            Text(
                                text = uiState.carInfoList[index].name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF4288C0))
                                    .padding(horizontal = 16.dp, vertical = 5.dp),
                                fontSize = 36.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    val carInfoList = listOf(CarInfoData(id = 1, carType = 1, name = "刚性矿卡", number = "冀F·1N7N9"))
    HomeScreen(uiState = HomeUiState(isLoading = false, carInfoList = carInfoList),
        onDemoClick = {},
        onRetryClick = {})
}