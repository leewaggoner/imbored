package com.wreckingball.imbored.ui.choose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wreckingball.imbored.ui.choose.models.ChooseActivityState

class ChooseActivityViewModel : ViewModel() {
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