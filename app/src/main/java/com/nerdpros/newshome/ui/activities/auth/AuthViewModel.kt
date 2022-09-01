package com.nerdpros.newshome.ui.activities.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerdpros.newshome.data.local.entity.UserEntity
import com.nerdpros.newshome.data.local.repo.UserRepository
import com.nerdpros.newshome.data.remote.response.Resource
import com.nerdpros.newshome.data.remote.repository.AuthRepository
import com.nerdpros.newshome.data.remote.response.DefaultResponse
import com.nerdpros.newshome.data.remote.response.LoginResponse
import com.nerdpros.newshome.data.remote.response.UserModelResponse
import com.nerdpros.newshome.model.Login
import com.nerdpros.newshome.model.SignUp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.HttpException

/**
 * @Author: Angatia Benson
 * @Date: 26/08/2022
 * Copyright (c) 2022 Bantechnis
 */
class AuthViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val repository = AuthRepository()
    private val disposable = CompositeDisposable()

    private val _signupResponse: MutableLiveData<Resource<DefaultResponse>> = MutableLiveData()
    val signupResponse: LiveData<Resource<DefaultResponse>>
        get() = _signupResponse

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun login(email: String, password: String) {
        _loginResponse.value = Resource.Loading
        val login = Login(email, password)
        disposable.add(
            repository.login(login)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LoginResponse>() {
                    override fun onSuccess(respose: LoginResponse) {
                        _loginResponse.value = Resource.Success(respose)
                    }

                    override fun onError(throwable: Throwable) {
                        when (throwable) {
                            is HttpException -> {
                                _loginResponse.value = Resource.Failure(
                                    false,
                                    throwable.code(),
                                    throwable.response()?.errorBody()
                                )
                            }
                            else -> {
                                _loginResponse.value = Resource.Failure(true, null, null)
                            }
                        }
                    }

                })
        )
    }

    fun saveUser(_user: UserModelResponse) = viewModelScope.launch {
        userRepository.saveUser(
            UserEntity(
                _user.id,
                _user.email,
                _user.name,
                _user.sessionToken
            )
        )
    }

    fun signup(email: String, name: String, password: String) {
        _signupResponse.value = Resource.Loading
        val signUp = SignUp(email, name, password)
        disposable.add(
            repository.signup(signUp)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<DefaultResponse>() {
                    override fun onSuccess(response: DefaultResponse) {
                        _signupResponse.value = Resource.Success(response)
                    }

                    override fun onError(throwable: Throwable) {
                        when (throwable) {
                            is HttpException -> {
                                _signupResponse.value = Resource.Failure(
                                    false,
                                    throwable.code(),
                                    throwable.response()?.errorBody()
                                )
                            }
                            else -> {
                                _signupResponse.value = Resource.Failure(true, null, null)
                            }
                        }
                    }

                })
        )
    }

}