package com.nerdpros.newshome.ui.activities.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nerdpros.newshome.data.remote.response.Resource
import com.nerdpros.newshome.data.remote.network.handleApiError
import com.nerdpros.newshome.databinding.ActivitySignUpBinding
import com.nerdpros.newshome.util.CustomDialog

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            createAccount()
        }

        binding.txtSignIn.setOnClickListener {
            finish()
        }

        setUpObservers()
    }

    private fun setUpObservers() {

        viewModel.signupResponse.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    CustomDialog.show(
                        "Creating Account",
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
                                finish()
                            }
                        }
                        else -> handleApiError(response as Resource.Failure) { createAccount() }
                    }
                }
            }

        }

    }

    private fun createAccount() {
        if (binding.edEmail.text.toString().isEmpty()) {
            binding.edEmail.error = "Field required"
            return
        }
        if (binding.edName.text.toString().isEmpty()) {
            binding.edName.error = "Field required"
            return
        }
        if (binding.edPassword.text.toString().isEmpty()) {
            binding.edPassword.error = "Field required"
            return
        }

        viewModel.signup(
            binding.edEmail.text.toString(),
            binding.edName.text.toString(),
            binding.edPassword.text.toString()
        )
    }

}