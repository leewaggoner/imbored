package com.wreckingball.imbored.ui.activity.models

import com.wreckingball.imbored.domain.models.BoredActivityImage

data class DisplayActivityState (
    val imageData: BoredActivityImage? = null,
    val activity: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)