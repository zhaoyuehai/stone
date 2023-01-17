package com.yuehai.stone.core.model

data class HttpData<T>(val code: Int?, val message: String?, val data: T? = null)