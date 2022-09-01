package com.nerdpros.newshome.ui.activities.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nerdpros.newshome.data.remote.response.Resource
import com.nerdpros.newshome.data.remote.repository.PlayersRepository
import com.nerdpros.newshome.data.remote.response.DefaultResponse
import com.nerdpros.newshome.data.remote.response.GetPlayerDetailsResponse
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
class PlayerProfileActivityViewModel : ViewModel() {
    private val repository = PlayersRepository()
    private val disposable = CompositeDisposable()

    private val _detailsResponse: MutableLiveData<Resource<GetPlayerDetailsResponse>> =
        MutableLiveData()
    val detailsResponse: LiveData<Resource<GetPlayerDetailsResponse>>
        get() = _detailsResponse

    private val _deleteResponse: MutableLiveData<Resource<DefaultResponse>> =
        MutableLiveData()
    val deleteResponse: LiveData<Resource<DefaultResponse>>
        get() = _deleteResponse

    fun getPlayerDetails(_id: String) {
        _detailsResponse.value = Resource.Loading
        disposable.add(
            repository.getPlayerDetails(_id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<GetPlayerDetailsResponse>() {

                    override fun onSuccess(response: GetPlayerDetailsResponse) {
                        _detailsResponse.value = Resource.Success(response)
                    }

                    override fun onError(throwable: Throwable) {
                        when (throwable) {
                            is HttpException -> {
                                _detailsResponse.value = Resource.Failure(
                                    false,
                                    throwable.code(),
                                    throwable.response()?.errorBody()
                                )
                            }
                            else -> {
                                _detailsResponse.value = Resource.Failure(true, null, null)
                            }
                        }
                    }

                })
        )
    }

    fun deletePlayer(_id: String) {
        _deleteResponse.value = Resource.Loading
        disposable.add(
            repository.deletePlayer(_id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<DefaultResponse>() {
                    override fun onSuccess(response: DefaultResponse) {
                        _deleteResponse.value= Resource.Success(response)
                    }

                    override fun onError(throwable: Throwable) {
                        when (throwable) {
                            is HttpException -> {
                                _deleteResponse.value = Resource.Failure(
                                    false,
                                    throwable.code(),
                                    throwable.response()?.errorBody()
                                )
                            }
                            else -> {
                                _deleteResponse.value = Resource.Failure(true, null, null)
                            }
                        }
                    }

                })
        )
    }

}