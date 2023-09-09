package com.wreckingball.imbored.ui.choose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wreckingball.imbored.R
import com.wreckingball.imbored.ui.choose.models.ChooseActivityState

class ChooseActivityViewModel : ViewModel() {
    val tabs = listOf(
        R.string.busywork,
        R.string.charity,
        R.string.cooking,
        R.string.diy,
        R.string.education,
        R.string.music,
        R.string.recreational,
        R.string.relaxation,
        R.string.social,
    )
    val participants = listOf(
        R.string.any,
        R.string.one,
        R.string.two,
        R.string.three,
        R.string.four,
        R.string.more,
    )
    val costs = listOf(
        R.string.any,
        R.string.free,
        R.string.cheap,
        R.string.moderate,
        R.string.expensive,
    )
    var state by mutableStateOf(ChooseActivityState())

    fun onTabClick(tabIndex: Int) {

    }

    fun onParticipantsSelected(participants: String) {
        state = state.copy(selectedParticipants = participants)
    }

    fun onCostSelected(cost: String) {
        state = state.copy(selectedCost = cost)
    }
}