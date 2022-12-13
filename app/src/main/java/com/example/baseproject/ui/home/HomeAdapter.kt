package com.example.baseproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.data.model.EmailObject
import com.example.baseproject.databinding.AdapterHomeBinding

class HomeAdapter constructor(private val itemListener: HomeAdapterItemListener)
    : ListAdapter<EmailObject, HomeAdapter.HomeViewHolder>(UserDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position), itemListener)
    }

    class HomeViewHolder private constructor(private val dataBinding: AdapterHomeBinding)
        : RecyclerView.ViewHolder(dataBinding.root){

        companion object{
            fun from(parent: ViewGroup): HomeViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = AdapterHomeBinding.inflate(inflater, parent, false)
                return HomeViewHolder(binding)
            }
        }

        fun bind(emailObject: EmailObject?, listener: HomeAdapterItemListener) {
            dataBinding.email = emailObject
            dataBinding.itemListener = listener
            dataBinding.executePendingBindings()
        }
    }

    class UserDiffUtil: DiffUtil.ItemCallback<EmailObject>(){
        override fun areItemsTheSame(oldItem: EmailObject, newItem: EmailObject): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: EmailObject, newItem: EmailObject): Boolean {
            return newItem.id == oldItem.id
//                    && newItem.firstName == oldItem.firstName
//                    && newItem.lastName == oldItem.lastName
        }

    }

    interface HomeAdapterItemListener {
        fun onItemClick(email: EmailObject)
    }

}