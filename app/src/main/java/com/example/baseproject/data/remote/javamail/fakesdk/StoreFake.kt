package com.example.baseproject.data.remote.javamail.fakesdk

import com.example.baseproject.common.BaseException
import com.example.baseproject.common.FakeServer
import com.example.baseproject.data.local.entities.FolderMail
import com.example.baseproject.data.remote.base.Result
import kotlinx.coroutines.delay

class StoreFake {
    suspend fun connect(accountEmail: String, password: String): Result<Boolean> {
        delay(1000L)
        //fake
        return Result.Success(true)
    }

    suspend fun getFolder(folderId: String?): FolderFake {
        delay(500L)
        // TODO: get folder by folderId
        return FolderFake()
    }

    suspend fun getListFolders(accountEmail: String): Result<List<FolderMail>> {
        delay(500L)
        return Result.Success(FakeServer.getListFakeFolders(accountEmail))
    }

    suspend fun getBody(accountEmail: String, emailId: String?): Result<String> {
        delay(1000L)
        if(emailId == null){
            return Result.Error(BaseException("emailId is null"))
        }
        return Result.Success("fake body from sv vvvvvvvvvvvvvvv")
    }
}