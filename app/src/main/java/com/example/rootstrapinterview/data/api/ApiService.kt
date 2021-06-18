package com.example.rootstrapinterview.data.api

import com.example.rootstrapinterview.data.api.model.character.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int) : CharacterResponse?
}