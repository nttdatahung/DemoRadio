package com.example.baseproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.ui.activities.MainActivityViewModel
import com.example.baseproject.ui.base.fragment.BaseFragment
import com.example.baseproject.data.model.EmailObject
import com.example.baseproject.databinding.FragmentHomeBinding
import com.example.baseproject.ui.detail.DetailFragment
import com.example.baseproject.ui.search.SearchFragment
import com.example.baseproject.utils.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var adapter: HomeAdapter
    private lateinit var dataBinding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentHomeBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        Log.d("HomeFragment", "onCreateView: ")
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(DetailFragment.KEY_DETAIL_FRAGMENT_RESULT) { _, _ ->
            Log.d("HomeFragment", "FragmentResultListener: ")
            homeViewModel.updateDataOnBackFromDetail(
                mainActivityViewModel.emailForDetailView,
                mainActivityViewModel.detailAction
            )
            mainActivityViewModel.emailForDetailView = null
            mainActivityViewModel.detailAction = null
        }
        setFragmentResultListener(SearchFragment.RESULT_BACK_FROM_SEARCH){_, _ ->
            Log.d("HomeFragment", "back from search")
        }
    }

//    override fun updateDataOnViewCreated() {
//        if (isBackFromDetailView()) {
//            homeViewModel.updateMails(mainActivityViewModel.emailForDetailView)
//        }
//    }

    override fun initView() {
        val itemClickListener = object : HomeAdapter.HomeAdapterItemListener {
            override fun onItemClick(email: EmailObject) {
                mainActivityViewModel.emailForDetailView = email
                openUserDetailFragment()
            }
        }
        adapter = HomeAdapter(itemClickListener)
        dataBinding.rcvHome.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        dataBinding.rcvHome.layoutManager = layoutManager
        AppCompatResources.getDrawable(requireContext(), R.drawable.home_divider)?.let { drawable ->
            val itemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(drawable)
            dataBinding.rcvHome.addItemDecoration(itemDecoration)
        }
    }

    override fun initListener() {
        dataBinding.btnRefresh.setOnClickListener {
            homeViewModel.refreshMails()
        }
        dataBinding.btnMenu.setOnClickListener {
            mainActivityViewModel.setBottomMenuState(true)
//            homeViewModel.deleteData()
        }
    }

    override fun observeDataChange() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.mailUiState.collect {
                    Log.d("HomeFragment", "observeDataChange: collect")
                    showLoading(it.isLoading)
                    adapter.submitList(it.mails){
                        if(it.scrollToTop){
                            dataBinding.rcvHome.smoothScrollToPosition(0)
                            homeViewModel.onRcvScrolledToTop()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.toastUiState.collect {
                    Log.d("HomeFragment", "observeDataChange: collect toast ${it.message}")
                    if (it.message != null) {
                        ToastUtil.show(it.message)
                        homeViewModel.onToastDismissed()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.refreshUiState.collect {
                    showUIRefreshing(it.isRefreshing)
                }
            }
        }
    }

    override fun initDataOnCreate() {
        Log.d("HomeFragment", "initData: ")
        homeViewModel.folderId = mainActivityViewModel.folderId
        homeViewModel.getMailsInitData()
    }

    private fun openUserDetailFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
        findNavController().navigate(action)
    }


    private fun showLoading(isShow: Boolean) {
        dataBinding.loadingView.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showUIRefreshing(isRefreshing: Boolean) {
        dataBinding.btnRefresh.text = if (isRefreshing) "Refreshing..." else "Pull to Refresh"
    }

    private fun isBackFromDetailView(): Boolean {
        return mainActivityViewModel.emailForDetailView != null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.cancelJobs()
    }
}