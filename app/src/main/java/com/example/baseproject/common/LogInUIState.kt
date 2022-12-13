package com.example.baseproject.common

class LogInUIState(val state: State, val exception: BaseException = BaseException("")) {
    enum class State {
        LOGIN_STATE_START,
        LOGIN_ERROR_COROUTINE_EXCEPTION,
        LOGIN_ERROR_NO_INTERNET,
        LOGIN_ERROR_INVALID_ACC_PASS,
        LOGIN_ERROR_UNKNOWN,
        LOGIN_SUCCESS_BUT_FAILED_TO_GET_FOLDERS,
        LOGIN_AND_GET_FOLDERS_SUCCESS
    }
}