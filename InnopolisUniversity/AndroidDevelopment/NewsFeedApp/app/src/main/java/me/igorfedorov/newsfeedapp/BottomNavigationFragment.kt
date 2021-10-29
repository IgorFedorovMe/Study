package me.igorfedorov.newsfeedapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import me.igorfedorov.newsfeedapp.databinding.FragmentBottomNavigationBinding

class BottomNavigationFragment : Fragment(R.layout.fragment_bottom_navigation) {

    private val binding: FragmentBottomNavigationBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupBottomNavigation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.news_feed_menu_item -> {
                    when (menuItem.isChecked) {
                        false -> {
                            binding.bottomNavigationFragmentContainerView.findNavController()
                                .navigate(R.id.news_feed_nav_graph)
                            true
                        }
                        true -> {
                            false
                        }
                    }
                }
                R.id.news_bookmarks_menu_item -> {
                    false
                }
                R.id.news_search_menu_item -> {
                    false
                }
                else -> throw IllegalStateException("Unexpected menu item")
            }
        }
    }
}