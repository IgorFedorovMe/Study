package me.igorfedorov.kinonline

import android.app.Application
import me.igorfedorov.kinonline.di.appModule
import me.igorfedorov.kinonline.di.ciceroneModule
import me.igorfedorov.kinonline.di.exoPlayerModule
import me.igorfedorov.kinonline.feature.movies_screen.di.moviesScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class KinOnlineApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@KinOnlineApp)
            modules(appModule, moviesScreenModule, ciceroneModule, exoPlayerModule)
        }
    }
}