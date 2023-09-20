package com.wreckingball.imbored

import android.app.Application
import com.wreckingball.imbored.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BoredApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BoredApplication)
            modules(appModule)
        }
    }
}