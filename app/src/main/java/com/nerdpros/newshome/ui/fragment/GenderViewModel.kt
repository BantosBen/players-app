package com.nerdpros.newshome.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nerdpros.newshome.data.remote.repository.PlayersRepository
import com.nerdpros.newshome.data.remote.response.GetPlayersResponse
import com.nerdpros.newshome.data.remote.response.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class GenderViewModel : ViewModel() {
    private val repository = PlayersRepository()
    private val disposable = CompositeDisposable()

    private val _playerResponse: MutableLiveData<Resource<GetPlayersResponse>> =
        MutableLiveData()
    val playerResponse: LiveData<Resource<GetPlayersResponse>>
        get() = _playerResponse


    fun getPlayersByGender(gender: String) {
        _playerResponse.value = Resource.Loading
        disposable.add(
            repository.getPlayersByGender(gender)
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
                                _playerResponse.value = Resource.Failure(
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