package com.wreckingball.imbored.ui.activity

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wreckingball.imbored.ui.Actions

@Composable
fun DisplayActivity(actions: Actions, boredUrl: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(
            text = "Display Activity!",
        )
    }
}