package com.example.baseproject.data.local

import com.example.baseproject.data.local.daos.EmailDao
import com.example.baseproject.data.local.entities.FolderMail
import com.example.baseproject.data.model.EmailObject
import com.example.baseproject.data.remote.base.Result
import kotlinx.coroutines.delay
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val emailDao: EmailDao
) {
    suspend fun getMailsFromDb(accountEmail: String, emailFolder: String?) :List<EmailObject> {
        delay(2000L)
        return emailDao.getAll()
    }

    suspend fun saveFolders(accountEmail: String, folders: List<FolderMail>) {
        delay(100L)
    }

    suspend fun saveToDB(listMails: List<EmailObject>) {
        delay(3000L)
        emailDao.insertAll(listMails)
    }

    suspend fun deleteAllMailsInFolder(accountEmail: String, folderId: String?) {
        delay(3000L)
        emailDao.deleteAll() /// TODO: delete by email and folder
    }

    suspend fun getBody(accountEmail: String, emailId: String?): Result<String>{
        // TODO: return bodyDao.getBody()
        delay(200L)
        return Result.Success("fake body zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz")
    }
}