package com.example.baseproject.data.repository

import com.example.baseproject.data.local.PrefsDataSource
import com.example.baseproject.data.local.entities.FolderMail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsRepository @Inject constructor(private val prefsDataSource: PrefsDataSource){
    fun getCurrentFolderId(): String {
        return prefsDataSource.getCurrentFolderId()
    }

    /**
     * run on UI thread
     */
    fun setCurrentFolderMail(accountEmail: String, folderIdInbox: FolderMail) {
        // TODO: implement
    }

    fun getCurrentAccountEmail(): String {
        return prefsDataSource.getCurrentAccountEmail()
    }

}