package com.example.baseproject.data.remote.base

import com.example.baseproject.common.BaseException

sealed class Result<out T: Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: BaseException) : Result<Nothing>()
}
