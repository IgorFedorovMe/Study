package me.igorfedorov.kinonline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import me.igorfedorov.kinonline.base.cicerone_navigation.Screens
import me.igorfedorov.kinonline.base.cicerone_navigation.common.BackButtonListener
import me.igorfedorov.kinonline.feature.movie_info_screen.ui.MovieInfoFragment
import me.igorfedorov.kinonline.feature.movies_screen.ui.MoviesListFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val navigatorHolder by inject<NavigatorHolder>()

    private val navigator: Navigator = object : AppNavigator(this, R.id.fragmentContainerView) {
        override fun applyCommands(commands: Array<out Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }

        override fun setupFragmentTransaction(
            screen: FragmentScreen,
            fragmentTransaction: FragmentTransaction,
            currentFragment: Fragment?,
            nextFragment: Fragment
        ) {
            if (currentFragment is MoviesListFragment && nextFragment is MovieInfoFragment)
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE)
        }
    }

    private val router by inject<Router>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigatorHolder.setNavigator(navigator)

        router.newRootScreen(Screens.moviesList())
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
}