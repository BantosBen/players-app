package com.nerdpros.newshome.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.nerdpros.newshome.R
import com.nerdpros.newshome.ui.activities.auth.SignInActivity
import com.nerdpros.newshome.ui.activities.main.MainActivity
import com.nerdpros.newshome.util.PrefManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.myLooper()!!).postDelayed({
            val intent = if (PrefManager().isLoggedIn()) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, SignInActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 3000)
    }
}