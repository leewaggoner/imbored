package com.wreckingball.imbored.network

import com.wreckingball.imbored.network.models.BoredActivityResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface BoredService {
    @GET
    suspend fun getActivity(@Url boredUrl: String) : BoredActivityResponse
}