package com.example.baseproject.common

class BaseException (
    val responseMessage: String? = null,
    val responseCode: Int = -1
) : Exception(responseMessage) {

    fun parseFromString(errorBody: String) {

    }

    constructor (throwable: Throwable): this(throwable.message)
}