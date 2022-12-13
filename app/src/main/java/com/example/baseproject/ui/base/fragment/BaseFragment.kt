package com.example.baseproject.ui.base.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("BaseFragment", "onCreate: ")
        initDataOnCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        updateDataOnViewCreated()
        initView()
        initListener()
        observeDataChange()
    }

    /**
     * called in onCreate
     */
    open fun initDataOnCreate(){}

    /**
     * Called in onViewCreated
     */
//    open fun updateDataOnViewCreated(){}

    /**
     * called in onViewCreated
     */
    open fun initView(){}

    /**
     * called in onViewCreated
     */
    open fun initListener(){}

    /**
     * called in onViewCreated
     */
    open fun observeDataChange(){}

    open fun showLoading(view: View, isShow: Boolean) {
        view.visibility = if(isShow) View.VISIBLE else View.GONE
    }

    override fun onStart() {
        super.onStart()
        Log.d("BaseFragment", "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("BaseFragment", "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d("BaseFragment", "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("BaseFragment", "onStop: ")
    }

    open fun navigate(directions: NavDirections) {
        findNavController().navigate(directions)
    }
}