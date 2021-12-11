package me.igorfedorov.kinonline.base.cicerone_navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import me.igorfedorov.kinonline.feature.movie_info_screen.ui.MovieInfoFragment
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie
import me.igorfedorov.kinonline.feature.movies_screen.ui.MoviesListFragment
import me.igorfedorov.kinonline.feature.video_player_screen.ui.VideoPlayerFragment

sealed class Screens : FragmentScreen {

    companion object ScreenKey {
        const val MOVIE_LIST_SCREEN = "MOVIE_LIST_SCREEN"
        const val MOVIE_INFO_SCREEN = "MOVIE_INFO_SCREEN"
        const val PLAYER_FRAGMENT_SCREEN = "PLAYER_FRAGMENT_SCREEN"
    }

    object MoviesList : Screens() {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return MoviesListFragment.newInstance()
        }

        override val screenKey: String
            get() = MOVIE_LIST_SCREEN
    }

    data class MovieInfo(val movie: Movie) : Screens() {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return MovieInfoFragment.newInstance(movie)
        }

        override val screenKey: String
            get() = MOVIE_INFO_SCREEN
    }

    data class PlayerFragment(val movie: Movie) : Screens() {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return VideoPlayerFragment.newInstance(movie)
        }

        override val screenKey: String
            get() = PLAYER_FRAGMENT_SCREEN

    }

    /*fun moviesList() = FragmentScreen() {
        MoviesListFragment.newInstance()
    }

    fun movieInfo(movie: Movie) = FragmentScreen {
        MovieInfoFragment.newInstance(movie)
    }

    fun playerFragment(movieUrl: String) = FragmentScreen() {
        VideoPlayerFragment.newInstance(movieUrl)
    }*/

}