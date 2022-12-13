package com.example.baseproject.ui.splash

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,

): BaseViewModel() {
    var isSplashTimeEnded = MutableLiveData<Boolean>()

    fun initNecessaryData() {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(ioDispatcher){
                Log.d(TAG, "initNecessaryData: ")
                isSplashTimeEnded.postValue(true)
            }
        }
    }

}