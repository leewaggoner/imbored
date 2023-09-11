package com.wreckingball.imbored.network.models

import com.google.gson.annotations.SerializedName

data class PexelResponse(
    @SerializedName("total_results")
    val totalResults: Int,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val photos: List<PexelImage>,
    @SerializedName("next_page")
    val nextPage: String
)
