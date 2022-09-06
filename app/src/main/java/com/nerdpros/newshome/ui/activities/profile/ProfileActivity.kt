package com.nerdpros.newshome.ui.activities.profile

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.onimur.handlepathoz.HandlePathOz
import br.com.onimur.handlepathoz.HandlePathOzListener
import br.com.onimur.handlepathoz.model.PathOz
import com.nerdpros.newshome.data.local.entity.UserEntity
import com.nerdpros.newshome.data.remote.network.handleApiError
import com.nerdpros.newshome.data.remote.response.Resource
import com.nerdpros.newshome.databinding.ActivityProfileBinding
import com.nerdpros.newshome.util.CustomDialog
import com.nerdpros.newshome.util.getProgressDrawable
import com.nerdpros.newshome.util.loadImage

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var handlePathOz: HandlePathOz
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var userEntity: UserEntity
    private val selectImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri.also {
                binding.profileImage.setImageURI(it)
                handlePathOz.getRealPath(it!!)
            }
        }
    private val handlePathOzListener = object : HandlePathOzListener.SingleUri {
        override fun onRequestHandlePathOz(pathOz: PathOz, tr: Throwable?) {
            viewModel.uploadProfile(pathOz.path)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handlePathOz = HandlePathOz(this, handlePathOzListener)
        binding.txtCamera.setOnClickListener { selectImage.launch("image/*") }
        binding.btnSave.setOnClickListener {
            updateAccount()
        }
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.userResponse.observe(this) { user ->
            userEntity = user
            binding.profileImage.loadImage(user.image, getProgressDrawable(baseContext))
            binding.edEmail.setText(user.email)
            binding.edName.setText(user.name)
        }

        viewModel.profileResponse.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    CustomDialog.show(
                        "Uploading profile",
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
                        }
                        else -> handleApiError(response as Resource.Failure) { }
                    }
                }
            }

        }

        viewModel.updateResponse.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    CustomDialog.show(
                        "Updating profile",
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
                            viewModel.updateUser(
                                UserEntity(
                                    userEntity.uid,
                                    binding.edEmail.text.toString(),
                                    binding.edName.text.toString(),
                                    userEntity.image,
                                    userEntity.sessionToken
                                )
                            )
                        }
                        else -> handleApiError(response as Resource.Failure) { }
                    }
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUser()
    }

    override fun onDestroy() {
        handlePathOz.deleteTemporaryFiles()
        handlePathOz.onDestroy()
        super.onDestroy()
    }

    private fun isValidMail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun updateAccount() {
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

        if (!isValidMail(binding.edEmail.text.toString().trim())) {
            binding.edEmail.error = "Invalid Email"
            return
        }

        viewModel.updateAccount(
            binding.edEmail.text.toString(),
            binding.edName.text.toString(),
            binding.edPassword.text.toString()
        )
    }

}