package com.example.baseproject.utils

import com.example.baseproject.MyApplication
import com.example.baseproject.data.local.entities.FolderMail
import com.example.baseproject.data.model.EmailObject

class Utils {
    companion object {

        fun getFolderIdInbox(accountEmail: String, folders: List<FolderMail>): FolderMail {
            // TODO: implement
            return FolderMail(accountEmail, "inbox", "inbox")
        }

        fun updateList(listMails: List<EmailObject>, email: EmailObject?) :List<EmailObject>{
            if(email == null){
                return listMails
            }
            val index = listMails.indexOfFirst { it.id == email.id }
            val mutableList = listMails.toMutableList()
            mutableList[index] = email
            return mutableList.toList()
        }

        fun removeFromList(listMails: List<EmailObject>, email: EmailObject?): List<EmailObject> {
            if(email == null){
                return listMails
            }
            val index = listMails.indexOfFirst { it.id == email.id }
            if(index == -1){
                return listMails
            }
            val mutableList = listMails.toMutableList()
            mutableList.removeAt(index)
            return mutableList.toList()
        }

        fun getLocalCountryCode(): String {
            // TODO: correct
            return "vn"
        }
    }
}