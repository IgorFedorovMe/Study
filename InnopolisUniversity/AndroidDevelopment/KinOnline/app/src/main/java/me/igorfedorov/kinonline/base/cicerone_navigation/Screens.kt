package me.igorfedorov.kinonline.base.cicerone_navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import me.igorfedorov.kinonline.feature.movie_info_screen.ui.MovieInfoFragment
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie
import me.igorfedorov.kinonline.feature.movies_screen.ui.MoviesListFragment
import me.igorfedorov.kinonline.feature.video_player_screen.ui.VideoPlayerFragment

object Screens {

    fun moviesList() = FragmentScreen() {
        MoviesListFragment.newInstance()
    }

    fun movieInfo(movie: Movie) = FragmentScreen {
        MovieInfoFragment.newInstance(movie)
    }

    fun playerFragment(movieUrl: String) = FragmentScreen() {
        VideoPlayerFragment.newInstance(movieUrl)
    }

}