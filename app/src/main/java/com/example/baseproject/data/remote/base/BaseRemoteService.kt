package com.example.baseproject.data.remote.base

import android.util.Log
import com.example.baseproject.common.BaseException
import retrofit2.Response

open class BaseRemoteService : BaseService() {

    protected suspend fun <T : Any> callApi(call: suspend () -> Response<T>): Result<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            Log.e("Frank","BaseRemoteService ${t.message}")
            return Result.Error(BaseException(t))
        }

        return if (response.isSuccessful) {
            if (response.body() == null) {
                Result.Error(BaseException(responseMessage =  "Response without body", responseCode = 200))
            } else {
                Result.Success(response.body()!!)
            }
        } else {
            val errorBody = response.errorBody()?.string() ?: ""
            Result.Error(parseError(response.message(), response.code(), errorBody))
        }
    }

}