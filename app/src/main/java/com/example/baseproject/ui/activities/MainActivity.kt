package com.example.baseproject.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseproject.R
import com.example.baseproject.ui.dialog.ModalBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val activityViewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityViewModel.validateAccount()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val content: View = findViewById(android.R.id.content)
            content.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener{
                    override fun onPreDraw(): Boolean {
                        return if(activityViewModel.splashUiState.value.isDataReady){
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
        observeDataChange()
    }

    private fun observeDataChange() {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                activityViewModel.bottomSheetUiState.collect{
                    if(it.isShow){
                        val bottomSheetDialogFragment = ModalBottomSheetDialogFragment()
                        bottomSheetDialogFragment.show(supportFragmentManager, "TAG")
                    }
                }
            }
        }
    }
}