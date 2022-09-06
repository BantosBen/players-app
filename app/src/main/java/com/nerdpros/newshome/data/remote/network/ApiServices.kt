package com.nerdpros.newshome.data.remote.network

import com.nerdpros.newshome.data.remote.response.DefaultResponse
import com.nerdpros.newshome.data.remote.response.GetPlayerDetailsResponse
import com.nerdpros.newshome.data.remote.response.GetPlayersResponse
import com.nerdpros.newshome.data.remote.response.LoginResponse
import com.nerdpros.newshome.model.Login
import com.nerdpros.newshome.model.SignUp
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * @Author: Angatia Benson
 * @Date: 25/08/2022
 * Copyright (c) 2022 Bantechnis
 */
interface ApiServices {
    @POST("login")
    fun login(@Body _login: Login): Single<LoginResponse>

    @POST("signup")
    fun signup(@Body _signup: SignUp): Single<DefaultResponse>

    @GET("players")
    fun getPlayers(): Single<GetPlayersResponse>

    @GET("players/view/{id}")
    fun getPlayerDetails(@Path("id") id: String): Single<GetPlayerDetailsResponse>

    @GET("players/filter/{gender}")
    fun getPlayersByGender(@Path("gender") gender: String): Single<GetPlayersResponse>

    @DELETE("players/delete/{id}")
    fun deletePlayer(@Path("id") id: String): Single<DefaultResponse>

    @PUT("account/update")
    fun updateAccount(@Body _signup: SignUp): Single<DefaultResponse>

    @DELETE("account/delete/{id}")
    fun deleteAccount(@Path("id") id: String): Single<DefaultResponse>

    @Multipart
    @POST("account/upload")
    fun uploadProfile(
        @Part part: MultipartBody.Part
    ): Single<DefaultResponse>
}