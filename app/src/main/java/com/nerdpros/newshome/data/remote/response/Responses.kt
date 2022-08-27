package com.nerdpros.newshome.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @Author: Angatia Benson
 * @Date: 26/08/2022
 * Copyright (c) 2022 Bantechnis
 */

data class DefaultResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String
)

data class LoginResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: UserModelResponse
)

data class UserModelResponse(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("session_token") val sessionToken: String
)

data class GetPlayersResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("players") val players: List<Player>
)

data class GetPlayerDetailsResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("player") val player: Player
)

data class Player(
    @SerializedName("id") val id: String,
    @SerializedName("first_name") val first: String,
    @SerializedName("last_name") val last: String,
    @SerializedName("rank") val rank: String,
    @SerializedName("age") val age: String,
    @SerializedName("points") val points: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("country") val country: String,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("description") val description: String,
)
