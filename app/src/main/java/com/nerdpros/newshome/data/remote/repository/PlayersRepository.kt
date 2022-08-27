package com.nerdpros.newshome.data.remote.repository

import com.nerdpros.newshome.data.remote.network.RetroClient
import com.nerdpros.newshome.data.remote.response.DefaultResponse
import com.nerdpros.newshome.data.remote.response.GetPlayerDetailsResponse
import com.nerdpros.newshome.data.remote.response.GetPlayersResponse
import io.reactivex.Single

/**
 * @Author: Angatia Benson
 * @Date: 27/08/2022
 * Copyright (c) 2022 Bantechnis
 */
class PlayersRepository {
    private val api = RetroClient.getApi()

    fun getPlayers(): Single<GetPlayersResponse> = api.getPlayers()

    fun getPlayerDetails(id: String): Single<GetPlayerDetailsResponse> = api.getPlayerDetails(id)

    fun getPlayersByGender(gender: String): Single<GetPlayersResponse> =
        api.getPlayersByGender(gender)

    fun deletePlayer(id: String): Single<DefaultResponse> = api.deletePlayer(id)
}