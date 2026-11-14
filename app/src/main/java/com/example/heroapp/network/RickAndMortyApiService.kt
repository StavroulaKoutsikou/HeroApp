package com.example.heroapp.network

import com.example.heroapp.model.ApiCharacter
import com.example.heroapp.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApiService {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int = 1
    ): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): ApiCharacter

    @GET("character")
    suspend fun searchCharacters(
        @Query("name") name: String,
        @Query("page") page: Int = 1
    ): CharacterResponse
}
