package com.wreckingball.imbored.ui.choose.models

import com.wreckingball.imbored.domain.models.BoredActivityImage

data class ChooseActivityState(
    val selectedParticipantsIndex: Int = 0,
    val selectedCostIndex: Int = 0,
    val imageData: BoredActivityImage? = null,
    val errorMessage: String = "",
    val selectedTabIndex: Int = 0,
)