package com.example.baseproject.common

class GettingDataState(val state: State, val message: String = "", val isDataEmpty: Boolean = false){
    enum class State {
        STATE_START,
        STATE_SUCCESS,
        STATE_ERROR
    }
}