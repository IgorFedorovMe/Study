package me.igorfedorov.newsfeedapp

import android.app.Application
import me.igorfedorov.newsfeedapp.di.appModule
import me.igorfedorov.newsfeedapp.di.dataBaseModule
import me.igorfedorov.newsfeedapp.feature.bookmarks_screen.di.bookmarksModule
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.di.mainScreenModule
import me.igorfedorov.newsfeedapp.feature.search_news_screen.di.searchNewsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class NewsFeedApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@NewsFeedApp)
            modules(appModule, mainScreenModule, dataBaseModule, bookmarksModule, searchNewsModule)
        }
    }
}