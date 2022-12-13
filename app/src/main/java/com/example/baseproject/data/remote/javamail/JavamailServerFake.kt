package com.example.baseproject.data.remote.javamail

import android.util.Log
import com.example.baseproject.common.BaseException
import com.example.baseproject.common.FakeServer
import com.example.baseproject.data.local.entities.FolderMail
import com.example.baseproject.data.model.EmailObject
import com.example.baseproject.data.remote.javamail.fakesdk.SessionFake
import com.example.baseproject.data.remote.javamail.fakesdk.StoreFake
import com.example.baseproject.data.remote.base.Result
import kotlinx.coroutines.delay

class JavamailServerFake private constructor() {
    private lateinit var session: SessionFake
    private var store: StoreFake? = null
    private val resultErrorStoreIsNull = Result.Error(BaseException("store is null", FakeServer.RESPONSE_CODE_NOT_LOGGED_IN))

    companion object {
        private lateinit var instance: JavamailServerFake
        fun getInstance(): JavamailServerFake {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = JavamailServerFake()
                }
            }
            return instance
        }
    }

    suspend fun logIn(accountEmail: String, password: String): Result<Boolean> {
        session = SessionFake()
        store = session.getStore("Imap")
        return store!!.connect(accountEmail, password)
    }

    suspend fun getListMails(folderId: String?, page: Int): Result<List<EmailObject>> {
        Log.d("JavamailServerFake", "getListMails: start")
        delay(5000L)
        store?.let {
            val folder = it.getFolder(folderId)
            val listMails = folder.getListMails(page)
            Log.d("JavamailServerFake", "getListMails: done")
            return listMails
        } ?: kotlin.run {
            return resultErrorStoreIsNull
        }
    }

    suspend fun getListFolders(accountEmail: String): Result<List<FolderMail>> {
        store?.let {
            return it.getListFolders(accountEmail)
        }?: kotlin.run {
            return resultErrorStoreIsNull
        }
    }

    suspend fun getBody(accountEmail: String, emailId: String?): Result<String> {
        store?.let {
            return it.getBody(accountEmail, emailId)
        }?: kotlin.run {
            return resultErrorStoreIsNull
        }
    }
}