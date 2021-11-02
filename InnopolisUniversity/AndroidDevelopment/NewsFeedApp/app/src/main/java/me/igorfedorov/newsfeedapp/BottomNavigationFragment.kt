package me.igorfedorov.newsfeedapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import me.igorfedorov.newsfeedapp.databinding.FragmentBottomNavigationBinding

class BottomNavigationFragment : Fragment(R.layout.fragment_bottom_navigation) {

    private val binding: FragmentBottomNavigationBinding by viewBinding(
        FragmentBottomNavigationBinding::bind
    )

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupBottomNavigation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navHostFragment = childFragmentManager.findFragmentById(
            R.id.bottomNavigationFragmentContainerView
        ) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.news, R.id.bookmarks, R.id.search)
        )
        val activity = requireActivity() as AppCompatActivity
        activity.setupActionBarWithNavController(navController, appBarConfiguration)
    }
}