package com.nerdpros.newshome.util

import android.content.Context
import android.content.SharedPreferences
import com.nerdpros.newshome.App

/**
 * @Author: Angatia Benson
 * @Date: 26/08/2022
 * Copyright (c) 2022 Bantechnis
 */
class PrefManager() {
    companion object {
        private const val PREF_NAME = "newshome"
        private const val SESSION_TOKEN = "session_token"
        private const val isFirstTimeShop = "isFirstTimeShop"
        private const val IS_LOGGED_IN = "IsLoggedIn"
        private const val PHONE = "phone"

    }

    private var pref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null


    init {
        pref = App.application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref!!.edit()
    }

    fun saveSessionToken(sessionToken: String) {
        editor!!.putString(SESSION_TOKEN, sessionToken)
        editor!!.putBoolean(IS_LOGGED_IN, true)
        editor!!.commit()
    }

    fun getSessionToken(): String? {
        return pref!!.getString(SESSION_TOKEN, "no-token")
    }

    fun isLoggedIn(): Boolean {
        return pref!!.getBoolean(IS_LOGGED_IN, false)
    }
}