package com.yuehai.stone.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PageOnError(modifier: Modifier = Modifier, onRetryClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(640.dp), contentAlignment = Alignment.Center
    ) { TextButton(onClick = onRetryClick) { Text(text = "请求失败，点击重试") } }
}

@Preview
@Composable
private fun PreviewPageOnError() {
    PageOnError {}
}