package me.igorfedorov.kinonline.feature.movies_screen.di

import com.github.terrakok.cicerone.Router
import me.igorfedorov.kinonline.feature.movies_screen.data.MoviesRepository
import me.igorfedorov.kinonline.feature.movies_screen.data.MoviesRepositoryImpl
import me.igorfedorov.kinonline.feature.movies_screen.data.api.MoviesApi
import me.igorfedorov.kinonline.feature.movies_screen.data.api.MoviesRemoteSource
import me.igorfedorov.kinonline.feature.movies_screen.domain.MoviesInteractor
import me.igorfedorov.kinonline.feature.movies_screen.ui.MoviesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val moviesScreenModule = module {

    single<MoviesApi> {
        get<Retrofit>().create()
    }

    single<MoviesRemoteSource> {
        MoviesRemoteSource(get<MoviesApi>())
    }

    single<MoviesRepository> {
        MoviesRepositoryImpl(get<MoviesRemoteSource>())
    }

    single<MoviesInteractor> {
        MoviesInteractor(get<MoviesRepository>())
    }

    viewModel<MoviesListViewModel> {
        MoviesListViewModel(
            get<MoviesInteractor>(),
            get<Router>()
        )
    }

}