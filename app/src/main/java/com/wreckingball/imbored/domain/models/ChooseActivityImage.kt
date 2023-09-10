package com.wreckingball.imbored.domain.models

data class ChooseActivityImage(
    val url: String,
    val photographer: String,
    val photographerUrl: String,
    val pexelsUrl: String = "https://www.pexels.com/"
)
