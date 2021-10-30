package me.igorfedorov.kinonline

import android.os.Bundle
import android.transition.ChangeBounds
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.*
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import me.igorfedorov.kinonline.base.cicerone_navigation.Screens
import me.igorfedorov.kinonline.base.cicerone_navigation.common.BackButtonListener
import me.igorfedorov.kinonline.feature.movie_info_screen.ui.MovieInfoFragment
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie
import me.igorfedorov.kinonline.feature.movies_screen.ui.MoviesListFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val BUNDLE_SCREEN_KEY = "BUNDLE_SCREEN_KEY"
        const val MOVIE_POSTER_TRANSITION = "MOVIE_POSTER_TRANSITION"
    }

    private var currentScreen: String? = null

    private val navigatorHolder by inject<NavigatorHolder>()

    private val navigator: Navigator = object : AppNavigator(this, R.id.fragmentContainerView) {
        override fun applyCommands(commands: Array<out Command>) {
            super.applyCommands(commands)
            commands.forEach { command ->
                when (command) {
                    is Forward -> {
                        currentScreen = command.screen.screenKey
                    }
                    is Replace -> {
                        currentScreen = command.screen.screenKey
                    }
                    is BackTo -> {
                        currentScreen = command.screen?.screenKey ?: ""
                    }
                    is Back -> {
                    }
                }
            }
            supportFragmentManager.executePendingTransactions()
        }

        override fun setupFragmentTransaction(
            screen: FragmentScreen,
            fragmentTransaction: FragmentTransaction,
            currentFragment: Fragment?,
            nextFragment: Fragment
        ) {
            if (currentFragment is MoviesListFragment && nextFragment is MovieInfoFragment)
                setupSharedElementForMoviesListToMovieInfo(
                    currentFragment,
                    nextFragment,
                    fragmentTransaction
                )
        }
    }

    private val router by inject<Router>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigatorHolder.setNavigator(navigator)

        if (savedInstanceState != null) {
            val movie = savedInstanceState.getParcelable<Movie>(MoviesListFragment.BUNDLE_MOVIE_KEY)
            if (movie != null && savedInstanceState.getString(BUNDLE_SCREEN_KEY) == Screens.MOVIE_INFO_SCREEN)
                router.navigateTo(Screens.MovieInfo(movie))
        } else {
            router.newRootScreen(Screens.MoviesList)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(BUNDLE_SCREEN_KEY, currentScreen)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        if (fragment != null && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return
        } else {
            super.onBackPressed()
        }
    }

    private fun setupSharedElementForMoviesListToMovieInfo(
        moviesListFragment: MoviesListFragment,
        movieInfoFragment: MovieInfoFragment,
        fragmentTransaction: FragmentTransaction
    ) {
        val changeBounds = ChangeBounds()
        movieInfoFragment.sharedElementEnterTransition = changeBounds
        movieInfoFragment.sharedElementReturnTransition = changeBounds
        moviesListFragment.sharedElementEnterTransition = changeBounds
        moviesListFragment.sharedElementReturnTransition = changeBounds
        val view = moviesListFragment.sharedView
        fragmentTransaction.addSharedElement(view!!, MOVIE_POSTER_TRANSITION)
    }
}