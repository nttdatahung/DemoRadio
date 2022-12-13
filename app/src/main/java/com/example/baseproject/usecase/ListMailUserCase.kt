package com.example.baseproject.usecase

import android.util.Log
import com.example.baseproject.common.FakeServer
import com.example.baseproject.data.model.EmailObject
import com.example.baseproject.data.remote.base.Result
import com.example.baseproject.data.repository.LogInRepository
import com.example.baseproject.data.repository.MailRepository
import com.example.baseproject.data.repository.PrefsRepository
import com.example.baseproject.di.IoDispatcher
import kotlinx.coroutines.*
import javax.inject.Inject

class ListMailUserCase @Inject constructor(
    private val mailRepository: MailRepository,
    private val prefsRepository: PrefsRepository,
    private val logInRepository: LogInRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getMailsFromServerWithRefreshToken(accountEmail: String, emailFolder: String?): Result<List<EmailObject>> = withContext(ioDispatcher){
        Log.d("ListMailUserCase", "getMailsFromServerWithRefreshToken: start")
        when(val mailResult = mailRepository.getMailsFromServer(accountEmail, emailFolder)){
            is Result.Error -> {
                Log.d("ListMailUserCase", "getMailsFromServer error: ${mailResult.exception.message}")
                if(mailResult.exception.responseCode == FakeServer.RESPONSE_CODE_NOT_LOGGED_IN){
                    when(val tokenResult = logInRepository.refreshToken(accountEmail)){
                        is Result.Error -> {
                            Log.d("ListMailUserCase", "refreshToken error: ${tokenResult.exception.message}")
                            tokenResult
                        }
                        is Result.Success -> {
                            Log.d("ListMailUserCase", "refreshToken: success")
                            val result = mailRepository.getMailsFromServer(accountEmail, emailFolder)
                            Log.d("ListMailUserCase", "getMailsFromServerWithRefreshToken: all done")
                            result
                        }
                    }
                } else {
                    mailResult
                }
            }
            is Result.Success -> {
                mailResult
            }
        }
    }

    suspend fun getMailsInitDataAndSaveToDB(accountEmail: String, emailFolder: String?): Result<List<EmailObject>> = withContext(ioDispatcher){
        Log.d("ListMailUserCase", "getMailsInitDataAndSaveToDB: start")
        val localMails = mailRepository.getMailsFromDB(accountEmail, emailFolder)
        if(localMails.isEmpty()){
            Log.d("ListMailUserCase", "getMailsInitDataAndSaveToDB: get mails from server")
            val result = getMailsFromServerWithRefreshToken(accountEmail, emailFolder)
            Log.d("ListMailUserCase", "getMailsInitDataAndSaveToDB: ${Thread.currentThread().name}")
            if(result is Result.Success) {
                launch {
                    Log.d("ListMailUserCase", "getMailsInitDataAndSaveToDB: save to db ${Thread.currentThread().name}")
                    try {
                        mailRepository.saveToDB(result.data)
                    } catch (e: Exception) {
                        Log.d("ListMailUserCase", "getMailsInitDataAndSaveToDB: save to db error")
                    }
                    Log.d("ListMailUserCase", "getMailsInitDataAndSaveToDB: save to db done ${Thread.currentThread().name}")
                }
            }
            Log.d("ListMailUserCase", "getMailsInitDataAndSaveToDB: done ${Thread.currentThread().name}")
            result
        } else{
            Result.Success(localMails)
        }
    }

}