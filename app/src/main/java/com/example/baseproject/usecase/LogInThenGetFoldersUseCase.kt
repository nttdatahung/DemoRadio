package com.example.baseproject.usecase

import com.example.baseproject.common.BaseException
import com.example.baseproject.data.local.LocalDataSource
import com.example.baseproject.data.local.entities.FolderMail
import com.example.baseproject.data.remote.base.Result
import com.example.baseproject.data.repository.FolderRepository
import com.example.baseproject.data.repository.LogInRepository
import com.example.baseproject.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogInThenGetFoldersUseCase @Inject constructor(
    private val logInRepository: LogInRepository,
    private val folderRepository: FolderRepository,
    private val localDataSource: LocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun logInThenGetFolders(
        accountEmail: String,
        password: String,
    ) =
        withContext(ioDispatcher) {
            when(val loginResult = logInRepository.logIn(accountEmail, password)){
                is Result.Error -> {
                    LoginUiState(LoginUiState.Type.LOGIN_ERROR,listOf(), loginResult.exception)
                }
                is Result.Success -> {
                    when (val folderResult = folderRepository.getListFolderFromServer(accountEmail)){
                        is Result.Error -> {
                            LoginUiState(LoginUiState.Type.LOGIN_SUCCESS_BUT_GET_FOLDER_FAILED,
                            listOf(), folderResult.exception)
                        }
                        is Result.Success -> {
                            LoginUiState(LoginUiState.Type.SUCCESSFULLY,
                            folderResult.data)
                        }
                    }
                }
            }
        }

    class LoginUiState (
        val type: Type,
        val folders: List<FolderMail> = listOf(),
        val exception: BaseException = BaseException(),
            ){

        enum class Type{
            LOGIN_ERROR,
            LOGIN_SUCCESS_BUT_GET_FOLDER_FAILED,
            SUCCESSFULLY
        }
    }
}