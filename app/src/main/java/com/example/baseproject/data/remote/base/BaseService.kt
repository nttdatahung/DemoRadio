package com.example.baseproject.data.remote.base

import com.example.baseproject.common.BaseException


abstract class BaseService {

    protected fun parseError(
        responseMessage: String?,
        responseCode: Int,
        errorBody: String?
    ): BaseException {

        val baseNetworkException =  BaseException(responseMessage,responseCode)
        errorBody?.let{
            baseNetworkException.parseFromString(it)
        }

        return baseNetworkException
    }

}