package com.yuehai.stone.feature.home.demo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yuehai.stone.core.model.CarTypeData
import com.yuehai.stone.core.ui.component.PageOnLoading
import com.yuehai.stone.feature.home.demo.DemoViewModel.*

@Composable
internal fun DemoScreen(viewModel: DemoViewModel, popBack: () -> Unit) {
    DemoScreen(
        uiState = viewModel.uiState, onBackClick = popBack, onItemClick = viewModel::clickButton
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DemoScreen(
    modifier: Modifier = Modifier,
    uiState: DemoUiState,
    onBackClick: () -> Unit,
    onItemClick: (CarTypeData) -> Unit
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
                .background(Color(0x4C009688))
                .padding(10.dp)
        ) {
            if (uiState.isLoading) {
                PageOnLoading()
            } else {
                uiState.carTypeList?.let {
                    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2), content = {
                        it.forEach { data ->
                            item {
                                Card(modifier = Modifier.padding(5.dp)) {
                                    Button(modifier = Modifier.height(height = 50.dp),
                                        onClick = { onItemClick(data) }) {
                                        Text(text = data.name)
                                    }
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDemoScreen() {
    DemoScreen(uiState = DemoUiState(), onItemClick = {}, onBackClick = {})
}