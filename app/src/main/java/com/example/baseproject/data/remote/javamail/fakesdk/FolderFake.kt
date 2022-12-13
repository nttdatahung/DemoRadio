package com.example.baseproject.data.remote.javamail.fakesdk

import com.example.baseproject.common.FakeServer
import com.example.baseproject.data.model.EmailObject
import com.example.baseproject.data.remote.base.Result
import kotlinx.coroutines.delay

class FolderFake {
    suspend fun getListMails(page: Int): Result<List<EmailObject>> {
        delay(1000L)
        return Result.Success(FakeServer.getListFakeEmails())
    }
}