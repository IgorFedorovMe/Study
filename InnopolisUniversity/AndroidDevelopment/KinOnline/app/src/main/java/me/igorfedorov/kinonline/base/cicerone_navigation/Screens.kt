package me.igorfedorov.kinonline.base.cicerone_navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import me.igorfedorov.kinonline.feature.movie_info_screen.ui.MovieInfoFragment
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie
import me.igorfedorov.kinonline.feature.movies_screen.ui.MoviesListFragment
import me.igorfedorov.kinonline.feature.video_player_screen.ui.VideoPlayerFragment

sealed class Screens : FragmentScreen {

    object MoviesList : Screens() {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return MoviesListFragment.newInstance()
        }
    }

    data class MovieInfo(val movie: Movie) : Screens() {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return MovieInfoFragment.newInstance(movie)
        }
    }

    data class PlayerFragment(val movieUrl: String) : Screens() {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return VideoPlayerFragment.newInstance(movieUrl)
        }

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