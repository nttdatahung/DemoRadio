package com.example.baseproject.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.common.LogInUIState
import com.example.baseproject.common.BaseException
import com.example.baseproject.data.repository.PrefsRepository
import com.example.baseproject.usecase.LogInThenGetFoldersUseCase
import com.example.baseproject.usecase.LogInThenGetFoldersUseCase.LoginUiState.Type.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.baseproject.common.LogInUIState.State
import com.example.baseproject.data.remote.base.Result
import com.example.baseproject.data.repository.FolderRepository
import com.example.baseproject.data.repository.StationRepository
import com.example.baseproject.utils.Utils

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInThenGetFoldersUseCase: LogInThenGetFoldersUseCase,
    private val folderRepository: FolderRepository,
    private val prefsRepository: PrefsRepository,
    private val stationRepository: StationRepository
) : BaseViewModel() {
    var logInUIState = MutableLiveData<LogInUIState>()

    fun testGettingStations(){
        viewModelScope.launch {
            val start = System.currentTimeMillis()
            Log.d("LoginViewModel", "testGettingStations: start")
            val result = stationRepository.searchStation("us", "votes", 10)
            Log.d("LoginViewModel", "testGettingStations: done, time: ${System.currentTimeMillis() - start}")
            when(result){
                is Result.Error -> Log.d("LoginViewModel", "testGettingStations: error + ${result.exception.message}")
                is Result.Success -> {
                    Log.d(
                        "LoginViewModel",
                        "testGettingStations: success, size: ${result.data.size}"
                    )
                    result.data.forEach{
                        Log.d("LoginViewModel", "testGettingStations: ${it.votes} ${it.clickcount} ${it.country}")
                    }
                    Log.d("LoginViewModel", "testGettingStations: ")
                }
            }
        }
    }

    fun logInThenGetListFolder(accountEmail: String, password: String) {
        logInUIState.value = LogInUIState(State.LOGIN_STATE_START)
        viewModelScope.launch(logInExceptionHandler) {
            val result = logInThenGetFoldersUseCase.logInThenGetFolders(
                accountEmail, password
            )
            Log.d("LoginViewModel", "logInThenGetListFolder thread: ${Thread.currentThread().name}")
            Log.d("LoginViewModel", "logInThenGetListFolder: ${result.type.name}")
            when (result.type) {
                LOGIN_ERROR -> {
                    logInUIState.value = LogInUIState(State.LOGIN_ERROR_UNKNOWN, result.exception)
                    //todo: handle different login error type
                }
                LOGIN_SUCCESS_BUT_GET_FOLDER_FAILED -> {
                    logInUIState.value = LogInUIState(
                        State.LOGIN_SUCCESS_BUT_FAILED_TO_GET_FOLDERS,
                        result.exception
                    )
                }
                SUCCESSFULLY -> {
                    folderRepository.saveFoldersToDb(accountEmail, result.folders)
                    prefsRepository.setCurrentFolderMail(accountEmail, Utils.getFolderIdInbox(accountEmail, result.folders))
                    logInUIState.value = LogInUIState(State.LOGIN_AND_GET_FOLDERS_SUCCESS)
                }
            }
        }
    }

    private val logInExceptionHandler = CoroutineExceptionHandler { _, exception ->
        logInUIState.value = LogInUIState(
            State.LOGIN_ERROR_COROUTINE_EXCEPTION,
            BaseException(exception)
        )

    }
}