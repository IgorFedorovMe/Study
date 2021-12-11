package me.igorfedorov.customviewapp

import android.app.Application
import me.igorfedorov.customviewapp.feature.canvas.di.canvasModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CustomViewApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CustomViewApp)
            modules(canvasModule)
        }

    }

}