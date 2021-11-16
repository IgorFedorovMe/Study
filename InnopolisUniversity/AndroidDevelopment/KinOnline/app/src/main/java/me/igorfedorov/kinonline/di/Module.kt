package me.igorfedorov.kinonline.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.google.android.exoplayer2.ExoPlayer
import me.igorfedorov.kinonline.exoplayer.VideoService
import me.igorfedorov.kinonline.exoplayer.VideoSource
import me.igorfedorov.kinonline.feature.movies_screen.data.MoviesRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val TEN_MB = 10L * 1024 * 1024
const val BASE_URL = "https://gist.githubusercontent.com/"
val appModule = module {

    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .cache(Cache(androidContext().cacheDir, TEN_MB))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<VideoService> {
        VideoService()
    }

}

val ciceroneModule = module {

    single<Cicerone<Router>> {
        Cicerone.create(Router())
    }

    single<NavigatorHolder> {
        get<Cicerone<Router>>().getNavigatorHolder()
    }

    single<Router> {
        get<Cicerone<Router>>().router
    }

    /*single {
        MainActivityNavigator(get<MainActivity>())
    }*/

}

val exoPlayerModule = module {

    factory<ExoPlayer> {
        ExoPlayer.Builder(androidApplication()).build()
    }

    factory<VideoSource> {
        VideoSource(moviesRepository = get<MoviesRepository>())
    }

}