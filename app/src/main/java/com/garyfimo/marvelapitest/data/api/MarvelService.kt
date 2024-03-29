package com.garyfimo.marvelapitest.data.api

import com.garyfimo.marvelapitest.data.model.MarvelServiceResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    @GET("characters")
    suspend fun getCharacters(
        @Query("hash") md5Digest: String,
        @Query("ts") timestamp: Long,
        @Query("apikey") apiKey: String,
        @Query("limit") limit: Int = 50,
    ): MarvelServiceResponse

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path(value = "id") id: Int,
        @Query("hash") md5Digest: String,
        @Query("ts") timestamp: Long,
        @Query("apikey") apiKey: String
    ): MarvelServiceResponse
}