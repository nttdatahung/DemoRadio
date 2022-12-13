package com.example.baseproject.data.remote.datasource

import com.example.baseproject.data.local.entities.FolderMail
import com.example.baseproject.data.remote.api.RadioApi
import com.example.baseproject.data.remote.base.BaseRemoteService
import com.example.baseproject.data.remote.base.Result
import com.example.baseproject.data.remote.javamail.JavamailServerFake
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderRemoteDataSource @Inject constructor(
    private val mainApi: RadioApi
): BaseRemoteService(){
    suspend fun getListFolders(accountEmail: String): Result<List<FolderMail>> {
        return JavamailServerFake.getInstance().getListFolders(accountEmail)
    }
}