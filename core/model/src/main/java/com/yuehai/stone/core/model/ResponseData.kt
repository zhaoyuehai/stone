package com.yuehai.stone.core.model

sealed class ResponseData<T>(open val message: String?, open val data: T?) {
    class Success<T>(override val data: T, override val message: String? = null) :
        ResponseData<T>(message = message, data = data)

    class Failure<T>(override val message: String?) :
        ResponseData<T>(message = message, data = null)
}