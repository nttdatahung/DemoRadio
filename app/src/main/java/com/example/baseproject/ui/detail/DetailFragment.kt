package com.example.baseproject.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.baseproject.ui.activities.MainActivityViewModel
import com.example.baseproject.ui.base.fragment.BaseFragment
import com.example.baseproject.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private val detailViewModel by viewModels<DetailViewModel>()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private var backPressedCallback: OnBackPressedCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityViewModel.detailAction = Action.UPDATE
        // This callback will only be called when MyFragment is at least Started.
        backPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Log.d("DetailFragment", "backPressedCallback ")
            setFragmentResult(KEY_DETAIL_FRAGMENT_RESULT, Bundle())
            findNavController().navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        binding.email = mainActivityViewModel.emailForDetailView
    }

    override fun initListener() {
        binding.btnDelete.setOnClickListener {
            mainActivityViewModel.detailAction = Action.DELETE
            backPressedCallback?.handleOnBackPressed()
        }
    }

    override fun observeDataChange() {
//        detailViewModel.updateUserState.observe(viewLifecycleOwner){ isUpdating ->
//            if(isUpdating){
//                Toast.makeText(context, "updating user...", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "finish", Toast.LENGTH_SHORT).show()
//                binding.user = mainActivityViewModel.selectedUser
//            }
//        }
    }

    override fun initDataOnCreate() {
        mainActivityViewModel.emailForDetailView?.let {
            detailViewModel.getEmailBody(
                "accountEmail", it
            )
        }
    }

    companion object {
        const val KEY_DETAIL_FRAGMENT_RESULT = "KEY_DETAIL_FRAGMENT_RESULT"
    }

    enum class Action {
        UPDATE,
        DELETE
    }

}