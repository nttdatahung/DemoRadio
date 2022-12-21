package com.example.baseproject.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.baseproject.R
import com.example.baseproject.databinding.ActivityMainBinding
import com.example.baseproject.ui.dialog.ModalBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val activityViewModel by viewModels<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val content: View = findViewById(android.R.id.content)
            content.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        return if (activityViewModel.splashUiState.value.isSplashScreenEnded) {
                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            //Nothing to do here, let the virtual SplashFragment handle the rest
                            true
                        } else {
                            false
                        }
                    }
                }
            )
        } else {
            //do nothing, so that SplashFragment keep showing on Screen
        }
        initNavigation()
        observeDataChange()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        val listOfNoBottomNavFragment = setOf<Int>(R.id.splashFragment, R.id.introFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (listOfNoBottomNavFragment.contains(destination.id)) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

    private fun observeDataChange() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.bottomSheetUiState.collect {
                    if (it.isShow) {
                        val bottomSheetDialogFragment = ModalBottomSheetDialogFragment()
                        bottomSheetDialogFragment.show(supportFragmentManager, "TAG")
                    }
                }
            }
        }
    }
}