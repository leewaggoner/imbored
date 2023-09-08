package com.example.imbored

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.imbored.ui.BoredHost
import com.example.imbored.ui.theme.ImBoredTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            ImBoredTheme(
                dynamicColor = false,
//                darkTheme = false,
            ) {
                BoredHost()
            }
        }
    }
}
