package me.igorfedorov.newsfeedapp.feature.news_feed_screen.di

import me.igorfedorov.newsfeedapp.common.utils.InternetAvailability
import me.igorfedorov.newsfeedapp.di.APP_MODULE_RETROFIT
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.MainScreenNewsApi
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.NewsRemoteSource
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.NewsRepository
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.NewsRepositoryImpl
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.use_case.get_last_hour_news_use_case.GetLastHourNewsUseCase
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.ui.MainScreenViewModel
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
        NewsRepositoryImpl(
            get<NewsRemoteSource>(),
            get<InternetAvailability>()
        )
    }

    single<GetLastHourNewsUseCase> {
        GetLastHourNewsUseCase(get<NewsRepository>())
    }

    viewModel(named(MAIN_SCREEN_VIEW_MODEL)) {
        MainScreenViewModel(get<GetLastHourNewsUseCase>())
    }

}