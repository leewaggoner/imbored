package com.wreckingball.imbored.ui.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wreckingball.imbored.domain.models.BoredActivityImage
import com.wreckingball.imbored.network.ApiResult
import com.wreckingball.imbored.repos.BoredActivity
import com.wreckingball.imbored.repos.PexelImages
import com.wreckingball.imbored.ui.activity.models.DisplayActivityState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DisplayActivityViewModel(
    private val boredActivity: BoredActivity,
    private val pexelImages: PexelImages,
) : ViewModel() {
    var state by mutableStateOf(DisplayActivityState())

    fun getActivityContent(boredUrl: String, tryAgain: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val activity = getActivity(boredUrl, tryAgain)
            val imageData = getImage(activity)
            state = state.copy(activity = activity, imageData = imageData)
        }
    }

    fun onDismissAlert() {
        state = state.copy(errorMessage = "")
    }

    private suspend fun getActivity(boredUrl: String, tryAgain: String): String {
        var activity = ""
        when (val result = boredActivity.getActivity(boredUrl, tryAgain)) {
            is ApiResult.Success -> {
                activity = result.data ?: ""
            }
            is ApiResult.Error -> {
                state = state.copy(errorMessage = "BoredAPI - ${result.message}")
            }
            is ApiResult.Loading -> { }
        }
        return activity
    }

    private suspend fun getImage(query: String): BoredActivityImage? {
        var imageData: BoredActivityImage? = null
        when (val result = pexelImages.getImageUrl(query)) {
            is ApiResult.Success -> {
                state = state.copy(isLoading = false)
                imageData = result.data
            }
            is ApiResult.Error -> {
                state = state.copy(errorMessage = "Pexel - ${result.message}")
            }
            is ApiResult.Loading -> { }
        }
        return imageData
    }
}