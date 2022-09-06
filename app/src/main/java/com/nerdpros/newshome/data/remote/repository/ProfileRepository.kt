package com.nerdpros.newshome.data.remote.repository

import com.nerdpros.newshome.data.remote.network.RetroClient
import com.nerdpros.newshome.data.remote.response.DefaultResponse
import com.nerdpros.newshome.data.remote.response.GetPlayersResponse
import com.nerdpros.newshome.model.SignUp
import io.reactivex.Single
import okhttp3.MultipartBody

/**
 * @Author: Angatia Benson
 * @Date: 06/09/2022
 * Copyright (c) 2022 Bantechnis
 */
class ProfileRepository {
    private val api = RetroClient.getApi()

    fun updateAccount(_signup: SignUp): Single<DefaultResponse> = api.updateAccount(_signup)

    fun deleteAccount(id: String): Single<DefaultResponse> = api.deleteAccount(id)

    fun uploadProfile(
        part: MultipartBody.Part
    ): Single<DefaultResponse> = api.uploadProfile(part)
}