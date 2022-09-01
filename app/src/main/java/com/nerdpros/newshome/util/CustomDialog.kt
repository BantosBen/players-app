package com.nerdpros.newshome.util

import android.widget.Toast
import androidx.fragment.app.FragmentManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.nerdpros.newshome.App


/**
 * @Author: Angatia Benson
 * @Date: 26/08/2022
 * Copyright (c) 2022 Bantechnis
 */
object CustomDialog {
    private lateinit var progressDialog: ProgressDialogFragment
    fun show(
        title: String = "This will only take a sec",
        message: String = "Loading",
        fragmentManager: FragmentManager
    ) {
        progressDialog = ProgressDialogFragment.newInstance(title, message)
        progressDialog.isCancelable = false
        progressDialog.show(fragmentManager, "progress")
    }

    fun dismiss() {
        progressDialog.dismiss()
    }

    fun toast(message: String) =
        Toast.makeText(App.application.applicationContext, message, Toast.LENGTH_LONG).show()
}