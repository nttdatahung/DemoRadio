package com.example.baseproject.data.repository

import android.text.TextUtils
import android.util.Log
import com.example.baseproject.data.local.LocalDataSource
import com.example.baseproject.data.local.PrefsDataSource
import com.example.baseproject.di.IoDispatcher
import com.example.baseproject.data.model.EmailObject
import com.example.baseproject.data.remote.base.Result
import com.example.baseproject.data.remote.datasource.MailRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MailRepository @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val mailRemoteDataSource: MailRemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val prefsDataSource: PrefsDataSource
) {

    suspend fun getMailsFromServer(accountEmail: String, emailFolder: String?): Result<List<EmailObject>> = withContext(ioDispatcher){
        mailRemoteDataSource.getListMails(emailFolder, 1)
    }

    suspend fun getMailsFromDB(accountEmail: String, emailFolder: String?): List<EmailObject> = withContext(ioDispatcher) {
        localDataSource.getMailsFromDb(accountEmail, emailFolder)
    }

    suspend fun saveToDB(listMails: List<EmailObject>) = withContext(ioDispatcher){
        localDataSource.saveToDB(listMails)
    }

    suspend fun deleteOldAndSaveNewMailsToDB(
        accountEmail: String,
        folderId: String?,
        listMails: List<EmailObject>
    ) = withContext(ioDispatcher){
        Log.d("MailRepository", "deleteOldAndSaveNewMailsToDB: start")
        localDataSource.deleteAllMailsInFolder(accountEmail, folderId)
        Log.d("MailRepository", "deleteOldAndSaveNewMailsToDB: deleted, start saving")
        localDataSource.saveToDB(listMails)
        Log.d("MailRepository", "deleteOldAndSaveNewMailsToDB: done")
    }

    suspend fun getBody(accountEmail: String, emailObject: EmailObject) = withContext(ioDispatcher){
        if(!TextUtils.isEmpty(emailObject.snippet)){
            localDataSource.getBody(accountEmail, emailObject.emailId)
        } else {
            mailRemoteDataSource.getBody(accountEmail, emailObject.emailId)
        }
    }

}