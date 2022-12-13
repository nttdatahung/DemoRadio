package com.example.baseproject.utils

import android.content.Context
import com.example.baseproject.BuildConfig
import com.example.baseproject.MyApplication

class SharedPrefsUtil {
    companion object {

        private fun getSharedPreferences() =
            MyApplication.instance.getSharedPreferences(
                BuildConfig.APPLICATION_ID,
                Context.MODE_PRIVATE
            )

        /**
         * Put value into sharedPrefs
         */
        fun <T> put(key: String, value: T) {
            val sharedPref = getSharedPreferences()
            with(sharedPref.edit()) {
                when (value) {
                    is Int -> {
                        putInt(key, value)
                        apply()
                    }
                    is String -> {
                        putString(key, value)
                        apply()
                    }
                    is Boolean -> {
                        putBoolean(key, value)
                        apply()
                    }
                    else -> {
                        throw Exception("Object type not matched")
                        // TODO: implement others value type
                    }
                }
            }
        }

        /**
         * get value from shared prefs
         */
        @Suppress("UNCHECKED_CAST")
        fun <T> get(key: String, defaultValue: T): T{
            val sharedPref = getSharedPreferences()
            return when(defaultValue){
                is Int -> {
                    sharedPref.getInt(key, defaultValue) as T
                }
                is String -> {
                    sharedPref.getString(key, defaultValue) as T
                }
                is Boolean -> {
                    sharedPref.getBoolean(key, defaultValue) as T
                }
                else -> {
                    throw Exception("Object type not matched")
                    // TODO: implement others value type
                }
            }
        }


    }
}