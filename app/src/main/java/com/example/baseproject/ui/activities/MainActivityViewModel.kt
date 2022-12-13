package com.example.baseproject.ui.activities

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.data.model.EmailObject
import com.example.baseproject.data.repository.PrefsRepository
import com.example.baseproject.di.IoDispatcher
import com.example.baseproject.ui.detail.DetailFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
): BaseViewModel() {
    var detailAction: DetailFragment.Action? = null
    var emailForDetailView: EmailObject? = null
    var folderId : String = ""
    private val _splashUiState = MutableStateFlow(SplashUiState())
    val splashUiState: StateFlow<SplashUiState> = _splashUiState.asStateFlow()
    private val _bottomSheetUiState = MutableStateFlow(BottomSheetUiState())
    val bottomSheetUiState: StateFlow<BottomSheetUiState> = _bottomSheetUiState.asStateFlow()

    init {
        folderId = prefsRepository.getCurrentFolderId()
    }

    fun validateAccount() {
        viewModelScope.launch(coroutineExceptionHandler) {
            Log.d("MainActivityViewModel", "validateAccount: launch")
            withContext(ioDispatcher){
                var accountEmail = prefsRepository.getCurrentAccountEmail()
                _splashUiState.update {
                    it.copy(isDataReady = true, isAccountAvailable = accountEmail.isNotEmpty())
                }
                Log.d("MainActivityViewModel", "validateAccount: done")
            }
        }
    }

    fun setBottomMenuState(isShow: Boolean) {
        _bottomSheetUiState.update { it.copy(isShow = isShow) }
    }

    fun onBottomSheetDismiss() {
        _bottomSheetUiState.update { it.copy(isShow = false) }
    }

    data class SplashUiState(
        var isDataReady: Boolean = false,
        var isAccountAvailable: Boolean = false
    )
    data class BottomSheetUiState(
        var isShow: Boolean = false
    )
}