package com.nerdpros.newshome.ui.activities.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerdpros.newshome.data.local.entity.UserEntity
import com.nerdpros.newshome.data.local.repo.UserRepository
import com.nerdpros.newshome.data.remote.repository.ProfileRepository
import com.nerdpros.newshome.data.remote.response.DefaultResponse
import com.nerdpros.newshome.data.remote.response.Resource
import com.nerdpros.newshome.model.SignUp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File


/**
 * @Author: Angatia Benson
 * @Date: 06/09/2022
 * Copyright (c) 2022 Bantechnis
 */
class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val repository = ProfileRepository()
    private val disposable = CompositeDisposable()

    private val _profileResponse: MutableLiveData<Resource<DefaultResponse>> = MutableLiveData()
    val profileResponse: LiveData<Resource<DefaultResponse>>
        get() = _profileResponse

    private val _updateResponse: MutableLiveData<Resource<DefaultResponse>> = MutableLiveData()
    val updateResponse: LiveData<Resource<DefaultResponse>>
        get() = _updateResponse

    private val _userResponse: MutableLiveData<UserEntity> = MutableLiveData()
    val userResponse: LiveData<UserEntity>
        get() = _userResponse


    fun getUser() = viewModelScope.launch {
        val userEntity = userRepository.getUser()
        _userResponse.postValue(userEntity)
    }

    fun updateUser(_userEntity: UserEntity) = viewModelScope.launch {
        userRepository.updateUser(_userEntity)
    }

    fun uploadProfile(
        filePath: String
    ) {
        _profileResponse.value = Resource.Loading
        val imageFile = File(filePath)
        val reqBody: RequestBody =
            imageFile.asRequestBody("multipart/form-file".toMediaTypeOrNull())
        val partImage = MultipartBody.Part.createFormData("avatar", imageFile.name, reqBody)
        disposable.add(
            repository.uploadProfile(partImage)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<DefaultResponse>() {
                    override fun onSuccess(response: DefaultResponse) {
                        _profileResponse.value = Resource.Success(response)
                    }

                    override fun onError(throwable: Throwable) {
                        when (throwable) {
                            is HttpException -> {
                                _profileResponse.value = Resource.Failure(
                                    false,
                                    throwable.code(),
                                    throwable.response()?.errorBody()
                                )
                            }
                            else -> {
                                throwable.printStackTrace()
                                _profileResponse.value = Resource.Failure(
                                    true,
                                    null,
                                    null
                                )
                            }
                        }
                    }

                })
        )
    }

    fun updateAccount(email: String, name: String, password: String) {
        _updateResponse.value = Resource.Loading
        val signUp = SignUp(email, name, password)
        disposable.add(
            repository.updateAccount(signUp)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<DefaultResponse>() {
                    override fun onSuccess(response: DefaultResponse) {
                        _updateResponse.value = Resource.Success(response)
                    }

                    override fun onError(throwable: Throwable) {
                        when (throwable) {
                            is HttpException -> {
                                _updateResponse.value = Resource.Failure(
                                    false,
                                    throwable.code(),
                                    throwable.response()?.errorBody()
                                )
                            }
                            else -> {
                                _updateResponse.value = Resource.Failure(
                                    true,
                                    null,
                                    null
                                )
                            }
                        }
                    }

                })
        )
    }

}