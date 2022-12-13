package com.example.baseproject.data.repository

import android.content.ContentValues
import android.util.Log
import com.example.baseproject.data.local.LocalDataSource
import com.example.baseproject.data.local.PrefsDataSource
import com.example.baseproject.data.local.entities.AccountEntity
import com.example.baseproject.data.remote.base.Result
import com.example.baseproject.data.remote.datasource.LoginDataSource
import com.example.baseproject.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogInRepository @Inject constructor(
    private val logInRemoteDataSource: LoginDataSource,
    private val localDataSource: LocalDataSource,
    private val prefsDataSource: PrefsDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun logIn(accountEmail: String, password: String) = withContext(ioDispatcher) {
        Log.d(ContentValues.TAG, "login: thread name1.1 = ${Thread.currentThread().name}")
        val result = logInRemoteDataSource.login(AccountEntity(0, accountEmail, password))
        if (result is Result.Success) {
            prefsDataSource.setCurrentAccountEmail(accountEmail)
//            prefsDataSource.saveToken(accountEmail, result.data )
            prefsDataSource.savePassword(password)
        }
        result
    }

    suspend fun refreshToken(accountEmail: String): Result<Boolean> {
        return logIn(accountEmail, prefsDataSource.getSavedPassword(accountEmail))
    }

}