package com.wreckingball.imbored.network.models

import com.google.gson.annotations.SerializedName

data class PexelImage(
    val id: Long,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    @SerializedName("photographer_url")
    val photographerUrl: String,
    val src: PexelImageSource,
)
