package com.example.baseproject.data.remote.datasource

import android.content.ContentValues.TAG
import android.util.Log
import com.example.baseproject.data.model.EmailObject
import com.example.baseproject.data.remote.api.RadioApi
import com.example.baseproject.data.remote.base.BaseRemoteService
import com.example.baseproject.data.remote.base.Result
import com.example.baseproject.data.remote.javamail.JavamailServerFake
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MailRemoteDataSource @Inject constructor(
    private val apiInterface: RadioApi
    ): BaseRemoteService() {

    suspend fun getListMails(folderId: String?, page: Int): Result<List<EmailObject>> {
        Log.d(TAG, "getListMails: ")
        return JavamailServerFake.getInstance().getListMails(folderId, page)
//        delay(1000L)
//        if(FakeServer.isValidToken(token)) {//fake response from server
//            return Result.Success(FakeServer.getListFakeEmails())
//        } else {
//            return Result.Error(BaseException("invalid token", FakeServer.RESPONSE_CODE_TOKEN_EXPIRED))
//        }
    }

    suspend fun getBody(accountEmail: String, emailId: String?): Result<String> {
        return JavamailServerFake.getInstance().getBody(accountEmail, emailId)
    }


}