package com.wreckingball.imbored.network

import com.wreckingball.imbored.models.PexelResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelImageService {
    @GET("v1/search")
    suspend fun getImage(
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 1,
    ) : PexelResponse
}