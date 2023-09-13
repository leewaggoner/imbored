package com.wreckingball.imbored.ui.choose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.wreckingball.imbored.domain.models.BoredActivityImage
import com.wreckingball.imbored.network.ApiResult
import com.wreckingball.imbored.repos.PexelImages
import com.wreckingball.imbored.ui.choose.models.ChooseActivityNavigation
import com.wreckingball.imbored.ui.choose.models.ChooseActivityState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ChooseActivityViewModel(
    handle: SavedStateHandle,
    private val pexelImages: PexelImages,
    ) : ViewModel() {
    val navigation = MutableSharedFlow<ChooseActivityNavigation>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )
    @OptIn(SavedStateHandleSaveableApi::class)
    var state by handle.saveable {
        mutableStateOf(ChooseActivityState())
    }
    private var curTab = ""
    private var curParticipants = ""
    private var curCost = ""

    fun onTabClick(tabIndex: Int, name: String) {
        state = state.copy(selectedTabIndex = tabIndex)
        curTab = name
        viewModelScope.launch(Dispatchers.IO) {
            val imageData = getImageData(name)
            state = state.copy(imageData = imageData)
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

    fun onDisplayActivity() {
        var url = "https://www.boredapi.com/api/activity?type=${curTab.lowercase()}"
        if (curParticipants.isNotEmpty()) {
            url += getParticipantsParam()
        }
        if (curCost.isNotEmpty()) {
            url += getCostParam()
        }
        viewModelScope.launch(Dispatchers.Main) {
            navigation.emit(ChooseActivityNavigation.DisplayActivity(url))
        }
    }

    fun onDismissAlert() {
        state = state.copy(errorMessage = "")
    }

    private suspend fun getImageData(name: String): BoredActivityImage? {
        var imageData: BoredActivityImage? = null
        when (val result = pexelImages.getImageUrl(name)) {
            is ApiResult.Success -> {
                imageData = result.data
            }
            is ApiResult.Error -> {
                state = state.copy(errorMessage = "Pexels - ${result.message}")
            }
            is ApiResult.Loading -> { }
        }
        return imageData
    }

    private fun getParticipantsParam(): String =
        when (curParticipants) {
            "Any" -> ""
            "More" -> "&participants=5"
            else -> "&participants=$curParticipants"
        }

    private fun getCostParam(): String =
        when (curCost) {
            "Any" -> ""
            "Free" -> "&price=0.0"
            "Cheap" -> "&minprice=0.1&maxprice=0.3"
            "Moderate" -> "&minprice=0.4&maxprice=0.7"
            "Expensive" -> "&minprice=0.8&maxprice=1.0"
            else -> ""
    }
}