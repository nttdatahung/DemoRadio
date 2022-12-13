package com.example.baseproject.ui.splash

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.data.remote.base.Result
import com.example.baseproject.data.repository.PrefsRepository
import com.example.baseproject.data.repository.StationRepository
import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val stationRepository: StationRepository,
    private val prefsRepository: PrefsRepository
): BaseViewModel() {
    private var _splashUiState = MutableStateFlow(SplashUiState())
    var splashUiState = _splashUiState.asStateFlow()

    fun initNecessaryData() {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(ioDispatcher){
                Log.d(TAG, "initNecessaryData: ")
                delay(500L)
                if(!prefsRepository.isIntroScreenShown()){
                    _splashUiState.update { it.copy(isSplashEnded = true, isNeedToShowIntro = true) }
                    when(val result = stationRepository.getRecommendStations()){
                        is Result.Error -> Log.d(TAG, "initNecessaryData: error")
                        is Result.Success -> {
                            Log.d(TAG, "initNecessaryData: success 1")
                            stationRepository.saveToDb(result.data)
                            Log.d(TAG, "initNecessaryData: success")
                        }
                    }
                } else {
                    _splashUiState.update { it.copy(isSplashEnded = true, isNeedToShowIntro = false) }
                }
            }
        }
    }

    data class SplashUiState(
        var isSplashEnded: Boolean = false,
        var isNeedToShowIntro: Boolean = false
    )

}