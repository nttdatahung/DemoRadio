package com.example.baseproject.common

import android.util.Log
import com.example.baseproject.data.local.entities.FolderMail
import com.example.baseproject.data.model.EmailObject

class FakeServer {
    companion object {
        const val RESPONSE_CODE_NOT_LOGGED_IN = 1434

        fun getFakeToken(): String {
            return System.currentTimeMillis().toString()
        }

        fun isValidToken(token: String): Boolean {
            val tokenLong = token.toLong()
            val isValid = System.currentTimeMillis() - tokenLong < 30 * 1000L
            Log.d("FakeServer", "isValidToken $tokenLong: $isValid")
            return isValid
        }

        fun getListFakeEmails(): List<EmailObject> {
            val listEmails = mutableListOf<EmailObject>()
            var emailObject: EmailObject?
            for (i in 0..19) {
                emailObject = EmailObject(
                    i.toLong(),
                    null,
                    RawData.senderNames[i],
                    RawData.senderEmail[i],
                    RawData.subject[i],
                    RawData.snippet[i]
                )
                listEmails.add(emailObject)
            }
            return listEmails.toList()
        }

        fun getListFakeFolders(accountEmail: String): List<FolderMail> {
            var listFolders = mutableListOf<FolderMail>()
            RawData.rawFolders.forEach {
                listFolders.add(FolderMail(accountEmail, it, it))
            }
            return listFolders
        }
    }
}