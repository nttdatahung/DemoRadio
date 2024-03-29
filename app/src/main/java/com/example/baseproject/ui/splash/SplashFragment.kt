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
import com.example.baseproject.ui.base.fragment.BaseFragment
import com.example.baseproject.databinding.FragmentSplashBinding
import com.example.baseproject.ui.activities.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment @Inject constructor() : BaseFragment<FragmentSplashBinding>() {
    private val splashViewModel by viewModels<SplashViewModel>()
    private val activityViewModel by activityViewModels<MainActivityViewModel>()

    override fun initDataOnCreate() {
        splashViewModel.initNecessaryData()
    }

    override fun observeDataChange() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.splashUiState.collect {
                    if (it.isSplashEnded) {
                        if (it.isNeedToShowIntro)
                            navigate(SplashFragmentDirections.actionSplashFragmentToIntroFragment())
                        else
                            navigate(SplashFragmentDirections.actionSplashFragmentToStationListFragment())
                        activityViewModel.onSplashScreenEnded()
                    }
                }
            }

        }
    }
}