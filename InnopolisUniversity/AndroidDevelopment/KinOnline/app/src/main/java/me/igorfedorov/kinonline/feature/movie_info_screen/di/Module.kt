package me.igorfedorov.kinonline.feature.movie_info_screen.di

import com.github.terrakok.cicerone.Router
import me.igorfedorov.kinonline.feature.movie_info_screen.ui.MovieInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieInfoModule = module {

    viewModel<MovieInfoViewModel> {
        MovieInfoViewModel(get<Router>())
    }

}