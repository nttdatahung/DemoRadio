package com.example.baseproject.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.baseproject.ui.base.fragment.BaseFragment
import com.example.baseproject.databinding.FragmentSplashBinding
import com.example.baseproject.ui.activities.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment @Inject constructor() : BaseFragment() {
    private lateinit var dataBinding: FragmentSplashBinding
    private val splashViewModel by viewModels<SplashViewModel>()
    private val activityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentSplashBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        Log.d("SplashFragment", "onCreateView: ")
        return dataBinding.root
    }

    override fun initDataOnCreate() {
        splashViewModel.initNecessaryData()
    }

    override fun observeDataChange() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.splashUiState.collect {
                    if (it.isDataReady) {
                        if (it.isAccountAvailable)
                            openHomeFragment()
                        else openLoginFragment()
                    }
                }
            }

        }
    }

    private fun openHomeFragment() {
        findNavController().navigate(
            SplashFragmentDirections.actionSplashFragmentToHomeFragment()
        )
    }

    private fun openLoginFragment() {
        findNavController().navigate(
            SplashFragmentDirections.actionSplashFragmentToLoginFragment()
        )
    }
}