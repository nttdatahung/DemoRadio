package com.example.baseproject.data.remote.datasource

import android.content.ContentValues
import android.util.Log
import com.example.baseproject.data.local.entities.AccountEntity
import com.example.baseproject.data.remote.api.RadioApi
import com.example.baseproject.data.remote.base.BaseRemoteService
import com.example.baseproject.data.remote.base.Result
import com.example.baseproject.data.remote.javamail.JavamailServerFake
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginDataSource @Inject constructor(
    private val mainApi: RadioApi
) : BaseRemoteService() {
    suspend fun login(accountEntity: AccountEntity): Result<Boolean> {
        Log.d(ContentValues.TAG, "login: ")
        return JavamailServerFake.getInstance().logIn(accountEntity.accountEmail!!, accountEntity.password!!)
//        return Result.Success(FakeServer.getFakeToken())
    }
}