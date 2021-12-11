package me.igorfedorov.kinonline

import android.app.Application
import me.igorfedorov.kinonline.di.appModule
import me.igorfedorov.kinonline.di.ciceroneModule
import me.igorfedorov.kinonline.di.exoPlayerModule
import me.igorfedorov.kinonline.feature.movie_info_screen.di.movieInfoModule
import me.igorfedorov.kinonline.feature.movies_screen.di.moviesScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class KinOnlineApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@KinOnlineApp)
            modules(
                appModule,
                moviesScreenModule,
                ciceroneModule,
                exoPlayerModule,
                movieInfoModule
            )
        }
    }
}