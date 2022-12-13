package com.example.baseproject.data.repository

import com.example.baseproject.data.local.LocalDataSource
import com.example.baseproject.data.local.entities.FolderMail
import com.example.baseproject.data.remote.datasource.FolderRemoteDataSource
import com.example.baseproject.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderRepository @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val folderRemoteDataSource: FolderRemoteDataSource,
    private val localDataSource: LocalDataSource
){
    suspend fun getListFolderFromServer(accountEmail: String) = withContext(ioDispatcher){
        folderRemoteDataSource.getListFolders(accountEmail)
    }

    suspend fun saveFoldersToDb(accountEmail: String, folders: List<FolderMail>) = withContext(ioDispatcher){
        delay(10)
        // TODO: implement
    }
}