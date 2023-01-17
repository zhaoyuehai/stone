package com.yuehai.stone.core.model

data class VersionData(
    val id: String,
    val versionName: String,
    val versionCode: Long,
    val miniVersionCode: Long,
    val desc: String,
    val downloadUrl: String
)