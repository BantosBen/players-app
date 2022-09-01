package com.nerdpros.newshome.data.remote.network

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.processphoenix.ProcessPhoenix
import com.nerdpros.newshome.App
import com.nerdpros.newshome.data.remote.response.Resource
import kotlinx.android.synthetic.main.fragment_male.*

/**
 * @Author: Angatia Benson
 * @Date: 26/08/2022
 * Copyright (c) 2022 Bantechnis
 */

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun AppCompatActivity.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> window.decorView.rootView.snackbar(
            "Please check your internet connection",
            retry
        )

        failure.errorCode == 401 -> {
            ProcessPhoenix.triggerRebirth(App.application.applicationContext);
        }

        else -> {
            val error = failure.errorBody?.string().toString()
            window.decorView.rootView.snackbar(error)
        }
    }
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> recyclerView.snackbar(
            "Please check your internet connection",
            retry
        )

        failure.errorCode == 401 -> {
            ProcessPhoenix.triggerRebirth(App.application.applicationContext);
        }

        else -> {
            val error = failure.errorBody?.string().toString()
            recyclerView.snackbar(error)
        }
    }
}