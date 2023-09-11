package com.wreckingball.imbored.ui.choose.models

sealed class ChooseActivityNavigation {
    data class DisplayActivity(val boredUrl: String) : ChooseActivityNavigation()
}