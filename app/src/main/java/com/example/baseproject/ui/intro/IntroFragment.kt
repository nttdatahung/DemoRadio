package com.example.baseproject.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.baseproject.databinding.FragmentIntroBinding
import com.example.baseproject.databinding.FragmentLoginBinding
import com.example.baseproject.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IntroFragment @Inject constructor(

): BaseFragment<FragmentIntroBinding>() {

    override fun initListener() {
        binding.btnDoneInto.setOnClickListener{
            findNavController().navigate(
                IntroFragmentDirections.actionIntroFragmentToStationListFragment()
            )
        }
    }
}