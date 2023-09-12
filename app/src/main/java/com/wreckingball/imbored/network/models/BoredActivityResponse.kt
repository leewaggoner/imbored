package com.wreckingball.imbored.network.models

data class BoredActivityResponse(
    val activity: String = "",
    val accessibility: Float = 0.0f,
    val type: String = "",
    val participants: Int = 0,
    val price: Float = 0.0f,
    val error: String? = null,
)
