package com.nerdpros.newshome.ui.activities.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nerdpros.newshome.data.remote.response.Resource
import com.nerdpros.newshome.data.remote.network.handleApiError
import com.nerdpros.newshome.databinding.ActivitySignInBinding
import com.nerdpros.newshome.ui.activities.main.MainActivity
import com.nerdpros.newshome.util.CustomDialog
import com.nerdpros.newshome.util.PrefManager

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.btnSignIn.setOnClickListener {
            login()
        }

        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.loginResponse.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    CustomDialog.show(
                        "Authenticating",
                        "Please wait...",
                        supportFragmentManager
                    )
                }
                else -> {
                    CustomDialog.dismiss()
                    when (response) {
                        is Resource.Success -> {
                            val value = response.value
                            CustomDialog.toast(value.message)
                            if (!value.error) {
                                viewModel.saveUser(value.user)
                                PrefManager().saveSessionToken(value.user.sessionToken)
                                startActivity(Intent(baseContext, MainActivity::class.java))
                                finish()
                            }
                        }
                        else -> handleApiError(response as Resource.Failure) { login() }
                    }
                }
            }
        }
    }

    private fun login() {
        if (binding.signinEdEmail.text.toString().isEmpty()) {
            binding.signinEdEmail.error = "Field Required"
            return
        }
        if (binding.signinEdPassword.text.toString().isEmpty()) {
            binding.signinEdEmail.error = "Field Required"
            return
        }

        viewModel.login(
            binding.signinEdEmail.text.toString(),
            binding.signinEdPassword.text.toString()
        )
    }
}