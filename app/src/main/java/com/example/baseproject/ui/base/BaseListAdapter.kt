package com.example.baseproject.ui.base

import androidx.recyclerview.widget.ListAdapter

class BaseListAdapter :ListAdapter<T, BaseListAdapter.BaseViewHolder>(BaseDiffUtil()){
    class BaseViewHolder {

    }
}