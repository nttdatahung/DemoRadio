package com.example.baseproject.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.baseproject.databinding.LayoutCustomSearchViewBinding

class CustomSearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private var binding: LayoutCustomSearchViewBinding
    init {
        binding = LayoutCustomSearchViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

}