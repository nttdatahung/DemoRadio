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

): BaseFragment() {
    private lateinit var binding: FragmentIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun initListener() {
        binding.btnDoneInto.setOnClickListener{
            findNavController().navigate(
                IntroFragmentDirections.actionIntroFragmentToStationListFragment()
            )
        }
    }
}