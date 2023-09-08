package com.example.imbored.ui

import android.app.Application
import com.example.imbored.di.appModule
import org.koin.core.context.startKoin

class BoredApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}