package com.nerdpros.newshome.ui.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nerdpros.newshome.data.remote.response.Resource
import com.nerdpros.newshome.data.remote.repository.PlayersRepository
import com.nerdpros.newshome.data.remote.response.GetPlayersResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

/**
 * @Author: Angatia Benson
 * @Date: 27/08/2022
 * Copyright (c) 2022 Bantechnis
 */
class MainActivityViewModel : ViewModel() {
    private val repository = PlayersRepository()
    private val disposable = CompositeDisposable()

    private val _playerResponse: MutableLiveData<Resource<GetPlayersResponse>> = MutableLiveData()
    val playerResponse: LiveData<Resource<GetPlayersResponse>>
        get() = _playerResponse

    fun getPlayers() {
        _playerResponse.value = Resource.Loading
        disposable.add(
            repository.getPlayers()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<GetPlayersResponse>() {
                    override fun onSuccess(response: GetPlayersResponse) {
                        _playerResponse.value = Resource.Success(response)
                    }

                    override fun onError(throwable: Throwable) {
                        when (throwable) {
                            is HttpException -> {
                                _playerResponse.value = Resource.Failure(
                                    false,
                                    throwable.code(),
                                    throwable.response()?.errorBody()
                                )
                            }
                            else -> {
                                _playerResponse.value = Resource.Failure(true, null, null)
                            }
                        }
                    }

                })
        )
    }

}