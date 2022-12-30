package com.example.baseproject.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.example.baseproject.databinding.LayoutCustomSearchViewBinding
import java.lang.reflect.ParameterizedType

class BaseCustomView<VB :ViewBinding> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private var _binding : VB? = null
    val binding :VB get() = _binding!!
    init {
//        binding = VB.inflate(LayoutInflater.from(context), this, true)
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<*>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        _binding = method.invoke(LayoutInflater.from(context), this, true) as VB
    }

}