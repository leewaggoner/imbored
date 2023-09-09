package com.wreckingball.imbored.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wreckingball.imbored.ui.activity.DisplayActivity
import com.wreckingball.imbored.ui.choose.ChooseActivity

@Composable
fun BoredHost() {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }

    var startDestination = Destinations.ChooseActivity

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.ChooseActivity) {
            ChooseActivity(actions = actions)
        }

        composable(Destinations.DisplayActivity) {
            DisplayActivity(actions = actions)
        }
    }
}