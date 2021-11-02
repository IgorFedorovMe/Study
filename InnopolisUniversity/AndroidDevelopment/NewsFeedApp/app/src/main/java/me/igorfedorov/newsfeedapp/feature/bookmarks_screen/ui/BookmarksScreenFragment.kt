package me.igorfedorov.newsfeedapp.feature.bookmarks_screen.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import me.igorfedorov.newsfeedapp.R
import me.igorfedorov.newsfeedapp.base.utils.setAdapterAndCleanupOnDetachFromWindow
import me.igorfedorov.newsfeedapp.base.utils.setData
import me.igorfedorov.newsfeedapp.databinding.FragmentBookmarksScreenBinding
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.ui.adapter.articleAdapterDelegate
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarksScreenFragment : Fragment(R.layout.fragment_bookmarks_screen) {

    private val viewModel: BookmarksScreenViewModel by viewModel()

    private val binding: FragmentBookmarksScreenBinding by viewBinding(
        FragmentBookmarksScreenBinding::bind
    )

    private val articlesAdapter by lazy {
        ListDelegationAdapter(
            articleAdapterDelegate(
                onItemClickListener = ::showCustomTab,
                onBookmarkClick = viewModel::deleteFromBookmarks
            )
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateUi()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewModel.viewState.observe(viewLifecycleOwner, ::render)
    }

    private fun render(viewState: ViewState) {
        articlesAdapter.setData(viewState.articles)
    }

    private fun initAdapter() {
        binding.rvBookmarks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            setAdapterAndCleanupOnDetachFromWindow(articlesAdapter)
        }
    }

    private fun showCustomTab(it: Article) {
        CustomTabsIntent.Builder()
            .build().apply {
                launchUrl(requireContext(), Uri.parse(it.url))
            }
    }

    private fun showCustomTab(it: Article) {
        CustomTabsIntent.Builder()
            .build().apply {
                launchUrl(requireContext(), Uri.parse(it.url))
            }
    }
}