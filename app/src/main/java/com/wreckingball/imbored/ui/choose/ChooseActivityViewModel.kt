package com.wreckingball.imbored.ui.choose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wreckingball.imbored.network.ApiResult
import com.wreckingball.imbored.repos.PexelImages
import com.wreckingball.imbored.ui.choose.models.ChooseActivityState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseActivityViewModel(
    private val pexelImages: PexelImages,
    ) : ViewModel() {
    var state by mutableStateOf(ChooseActivityState())
    private var curTab = ""
    private var curParticipants = ""
    private var curCost = ""

    fun onTabClick(name: String) {
        curTab = name
        viewModelScope.launch(Dispatchers.IO) {
            val url = getImageUrl(name)
            state = state.copy(imageUrl = url)
        }
    }

    fun onParticipantsSelected(participants: String) {
        curParticipants = participants
        state = state.copy(selectedParticipants = participants)
    }

    fun onCostSelected(cost: String) {
        curCost = cost
        state = state.copy(selectedCost = cost)
    }

    private suspend fun getImageUrl(name: String) : String {
        var url = ""
        when (val result = pexelImages.getImageUrl(name)) {
            is ApiResult.Success -> {
                url = result.data ?: ""
            }
            is ApiResult.Error -> {}
            is ApiResult.Loading -> {}
        }
        return url
    }
}