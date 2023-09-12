package com.wreckingball.imbored.ui.choose.models

import com.wreckingball.imbored.domain.models.BoredActivityImage

data class ChooseActivityState(
    val selectedParticipants: String = "",
    val selectedCost: String = "",
    val imageData: BoredActivityImage? = null,
)