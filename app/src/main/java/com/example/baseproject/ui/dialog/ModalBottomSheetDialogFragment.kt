package com.example.baseproject.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.baseproject.R
import com.example.baseproject.ui.activities.MainActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheetDialogFragment: BottomSheetDialogFragment() {
    private val activityViewModel by activityViewModels<MainActivityViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog_home, container, false)
    }


    override fun onDismiss(dialog: DialogInterface) {
        Log.d("BottomSheetDialog", "onDismiss: ")
        activityViewModel.onBottomSheetDismiss()
        super.onDismiss(dialog)
    }

}