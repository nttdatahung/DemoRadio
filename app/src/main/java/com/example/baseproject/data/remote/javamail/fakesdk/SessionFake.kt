package com.example.baseproject.data.remote.javamail.fakesdk

class SessionFake {
    fun getStore(imap: String): StoreFake {
        return StoreFake()
    }
}