package com.wreckingball.imbored.ui.choose.models

import android.os.Parcelable
import com.wreckingball.imbored.domain.models.BoredActivityImage
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChooseActivityState(
    val selectedParticipantsIndex: Int = 0,
    val selectedCostIndex: Int = 0,
    val imageData: BoredActivityImage? = null,
    val errorMessage: String = "",
    val selectedTabIndex: Int = 0,
): Parcelable
