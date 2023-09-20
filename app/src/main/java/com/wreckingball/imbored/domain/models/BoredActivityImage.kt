package com.wreckingball.imbored.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BoredActivityImage(
    val url: String = "",
    val photographer: String = "",
    val photographerUrl: String = "",
    val imageHost: String = "Pexels",
    val imageHostUrl: String = "https://www.pexels.com/",
) : Parcelable
