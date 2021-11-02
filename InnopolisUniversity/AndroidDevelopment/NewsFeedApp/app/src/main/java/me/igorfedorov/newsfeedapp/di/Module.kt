package me.igorfedorov.newsfeedapp.di

import androidx.room.Room
import me.igorfedorov.newsfeedapp.BuildConfig
import me.igorfedorov.newsfeedapp.base.data_base.BookmarksDatabase
import me.igorfedorov.newsfeedapp.base.utils.InternetAvailability
import me.igorfedorov.newsfeedapp.di.util.ApiKeyInterceptor
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.local.dao.ArticleDao
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://newsapi.org/"
const val APP_MODULE_OKHTTP = "APP_MODULE_OKHTTP"
const val APP_MODULE_RETROFIT = "APP_MODULE_RETROFIT"
const val TEN_MB = 10L * 1024 * 1024
val appModule = module {

    single<OkHttpClient>(named(APP_MODULE_OKHTTP)) {
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(BuildConfig.NEWS_API_KEY))
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .cache(Cache(androidContext().cacheDir, TEN_MB))
            .build()
    }

    single<Retrofit>(named(APP_MODULE_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>(qualifier = named(APP_MODULE_OKHTTP)))
            .build()
    }

    single<InternetAvailability> {
        InternetAvailability(androidContext())
    }
}

val dataBaseModule = module {
    single<BookmarksDatabase> {
        Room.databaseBuilder(
            androidContext(),
            BookmarksDatabase::class.java,
            BookmarksDatabase.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single<ArticleDao> {
        get<BookmarksDatabase>().bookmarksDao()
    }
}