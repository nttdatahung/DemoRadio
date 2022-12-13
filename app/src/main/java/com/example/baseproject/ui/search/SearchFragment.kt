package com.example.baseproject.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.baseproject.databinding.FragmentSearchBinding
import com.example.baseproject.ui.activities.MainActivityViewModel
import com.example.baseproject.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: BaseFragment() {
    lateinit var dataBinding:FragmentSearchBinding
    private val activityViewModel by activityViewModels<MainActivityViewModel>()
    private var backPressedCallback : OnBackPressedCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backPressedCallback = requireActivity().onBackPressedDispatcher.addCallback (this) {
            setFragmentResult(RESULT_BACK_FROM_SEARCH, Bundle())
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentSearchBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        return dataBinding.root
    }

    companion object{
        const val RESULT_BACK_FROM_SEARCH = "RESULT_BACK_FROM_SEARCH"
    }
}