package me.igorfedorov.newsfeedapp.feature.news_feed_screen.di

import me.igorfedorov.newsfeedapp.di.APP_MODULE_RETROFIT
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.MainScreenNewsApi
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.NewsRemoteSource
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.NewsRepository
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.NewsRepositoryImpl
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.NewsFeedInteractor
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.ui.NewsFeedScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

const val MAIN_SCREEN_VIEW_MODEL = "MAIN_SCREEN_VIEW_MODEL"
val mainScreenModule = module {

    single<MainScreenNewsApi> {
        get<Retrofit>(qualifier = named(APP_MODULE_RETROFIT)).create()
    }

    single<NewsRemoteSource> {
        NewsRemoteSource(get<MainScreenNewsApi>())
    }

    single<NewsRepository> {
        NewsRepositoryImpl(get<NewsRemoteSource>())
    }

    single<NewsFeedInteractor> {
        NewsFeedInteractor(get<NewsRepository>())
    }

    viewModel(named(MAIN_SCREEN_VIEW_MODEL)) {
        NewsFeedScreenViewModel(get<NewsFeedInteractor>())
    }
}