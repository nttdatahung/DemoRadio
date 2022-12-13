package com.example.baseproject.data.local

import android.util.Log
import com.example.baseproject.utils.Constants
import com.example.baseproject.utils.SharedPrefsUtil
import javax.inject.Inject

class PrefsDataSource @Inject constructor(){
    fun getCurrentFolderId(): String {
        return SharedPrefsUtil.get(Constants.KEY_CURRENT_FOLDER_ID, "")
    }

//    fun saveToken(accountEmail: String, token: String) {
//        Log.d("PrefsDataSource", "saveToken: $token")
//        SharedPrefsUtil.put(Constants.KEY_TOKEN + accountEmail.replace("@", ""), token)
//    }

    fun getToken(accountEmail: String) :String{
        val token = SharedPrefsUtil.get(Constants.KEY_TOKEN + accountEmail.replace("@", ""), "")
        Log.d("PrefsDataSource", "getToken: $token")
        return token
    }

    fun setCurrentAccountEmail(accountEmail: String) {
        SharedPrefsUtil.put(Constants.KEY_CURRENT_ACCOUNT_EMAIL, accountEmail)
    }

    fun getCurrentAccountEmail(): String{
        return SharedPrefsUtil.get(Constants.KEY_CURRENT_ACCOUNT_EMAIL, "")
    }

    fun savePassword(password: String) {
        // TODO: save to SharedPrefs
    }

    fun getSavedPassword(accountEmail: String): String {
        // TODO: implement
        return "fake password"
    }
}