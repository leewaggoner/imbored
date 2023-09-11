package com.wreckingball.imbored.ui

import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Destinations {
    const val ChooseActivity = "ChooseActivity"
    const val DisplayActivity = "DisplayActivity/{boredUrl}"
}

class Actions(navController: NavController) {
    val navigateToDisplay: (String) -> Unit = { boredUrl ->
        val encodedUrl = URLEncoder.encode(boredUrl, StandardCharsets.UTF_8.toString())
        navController.navigate(
            Destinations.DisplayActivity.replace(
                oldValue = "{boredUrl}",
                newValue = encodedUrl
            )
        )
    }
}