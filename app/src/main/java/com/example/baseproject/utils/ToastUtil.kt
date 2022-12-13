package com.example.baseproject.utils

import android.widget.Toast
import com.example.baseproject.MyApplication

class ToastUtil {
    companion object{
        private var mToast = Toast(MyApplication.instance)
        fun show(text: String){
            mToast.cancel()
            mToast = Toast.makeText(MyApplication.instance, text, Toast.LENGTH_SHORT)
            mToast.show()
        }
    }
}