package com.wreckingball.imbored.ui

import androidx.navigation.NavController

object Destinations {
    const val ChooseActivity = "ChooseActivity"
    const val DisplayActivity = "DisplayActivity"
}
class Actions(navController: NavController) {
    val navigateToDisplay: () -> Unit = {
        navController.navigate(Destinations.DisplayActivity)
    }
}