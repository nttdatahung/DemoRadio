package com.example.baseproject.ui.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.baseproject.ui.base.fragment.BaseFragment
import com.example.baseproject.common.LogInUIState.State.*
import com.example.baseproject.databinding.FragmentLoginBinding
import com.example.baseproject.utils.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment @Inject constructor() : BaseFragment<FragmentLoginBinding>() {
    private val loginViewModel by viewModels<LoginViewModel>()


    override fun initListener() {
        binding.btnLogIn.setOnClickListener {
            loginViewModel.testGettingStations()
//            loginViewModel.logInThenGetListFolder(
//                // TODO: correct data
//                "testmail@ntt.com", "testpassword"
////                dataBinding.edtEmail.text.toString(), dataBinding.edtPassword.text.toString()
//            )
        }
    }

    override fun observeDataChange() {
        loginViewModel.logInUIState.observe(viewLifecycleOwner){
            when(it.state){
                LOGIN_STATE_START -> {
                    showLoading(binding.loadingView, true)
                    ToastUtil.show("Logging in...")
                }
                LOGIN_ERROR_COROUTINE_EXCEPTION -> {
                    Log.d(TAG, "LoginFragment observeDataChange error: ${it.exception.message}")
                }
                LOGIN_ERROR_NO_INTERNET -> TODO()
                LOGIN_ERROR_INVALID_ACC_PASS -> TODO()
                LOGIN_ERROR_UNKNOWN -> TODO()
                LOGIN_SUCCESS_BUT_FAILED_TO_GET_FOLDERS -> TODO()
                LOGIN_AND_GET_FOLDERS_SUCCESS -> {
                    ToastUtil.show("Log in success")
                    openHomeFragment()
                }
            }
        }
    }

    private fun openHomeFragment() {
//        findNavController().navigate(
//            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
//        )
    }
}