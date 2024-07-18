package com.abdts.musicplayerpractice

import android.app.Application
import com.abdts.musicplayerpractice.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class VibApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
            androidContext(this@VibApplication)
            androidLogger()
        }
    }
}