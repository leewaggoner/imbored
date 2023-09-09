package com.wreckingball.imbored.network

import retrofit2.http.GET
import retrofit2.http.Path

interface PexelImageService {
    @GET("v1/search?query={query}page=1&per_page=1&orientation=square")
    suspend fun getImage(@Path("query") query: String) : PexelImage
}