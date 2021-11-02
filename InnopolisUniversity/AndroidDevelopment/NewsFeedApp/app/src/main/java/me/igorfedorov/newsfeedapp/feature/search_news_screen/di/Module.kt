package me.igorfedorov.newsfeedapp.feature.search_news_screen.di

import me.igorfedorov.newsfeedapp.di.APP_MODULE_RETROFIT
import me.igorfedorov.newsfeedapp.feature.search_news_screen.data.SearchNewsRepository
import me.igorfedorov.newsfeedapp.feature.search_news_screen.data.SearchNewsRepositoryImpl
import me.igorfedorov.newsfeedapp.feature.search_news_screen.data.api.SearchNewsApi
import me.igorfedorov.newsfeedapp.feature.search_news_screen.data.api.SearchNewsRemoteSource
import me.igorfedorov.newsfeedapp.feature.search_news_screen.domain.SearchNewsInteractor
import me.igorfedorov.newsfeedapp.feature.search_news_screen.ui.SearchNewsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val searchNewsModule = module {

    single<SearchNewsApi> {
        get<Retrofit>(qualifier = named(APP_MODULE_RETROFIT)).create()
    }

    single<SearchNewsRemoteSource> {
        SearchNewsRemoteSource(get<SearchNewsApi>())
    }

    single<SearchNewsRepository> {
        SearchNewsRepositoryImpl(get<SearchNewsRemoteSource>())
    }

    single<SearchNewsInteractor> {
        SearchNewsInteractor(get<SearchNewsRepository>())
    }

    viewModel<SearchNewsScreenViewModel> {
        SearchNewsScreenViewModel(get<SearchNewsInteractor>())
    }

}