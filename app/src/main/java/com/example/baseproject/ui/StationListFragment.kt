package com.example.baseproject.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.baseproject.databinding.FragmentSplashBinding
import com.example.baseproject.databinding.FragmentStationListBinding
import com.example.baseproject.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StationListFragment @Inject constructor(

) :BaseFragment(){
    private lateinit var binding: FragmentStationListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStationListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        Log.d("SplashFragment", "onCreateView: ")
        return binding.root
    }
}