package me.igorfedorov.kinonline.feature.movies_screen.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import me.igorfedorov.kinonline.MainActivity
import me.igorfedorov.kinonline.R
import me.igorfedorov.kinonline.base.utils.lazyFast
import me.igorfedorov.kinonline.base.utils.setAdapterAndCleanupOnDetachFromWindow
import me.igorfedorov.kinonline.base.utils.setData
import me.igorfedorov.kinonline.databinding.FragmentMoviesListBinding
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie
import me.igorfedorov.kinonline.feature.movies_screen.ui.adapter.MoviesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    companion object {
        const val BUNDLE_MOVIE_KEY = "BUNDLE_MOVIE_KEY"

        fun newInstance() = MoviesListFragment()
    }

    private val binding: FragmentMoviesListBinding by viewBinding(FragmentMoviesListBinding::bind)

    private val viewModel: MoviesListViewModel by viewModel()

    private var movie: Movie? = null

    private var viewForTransition: View? = null

    private val moviesAdapter: MoviesAdapter by lazyFast {
        MoviesAdapter(
            onItemClick = { movie, layoutPosition ->
                this.movie = movie
                binding.moviesListRecyclerView.scrollToPosition(layoutPosition)
                viewModel.processUiEvent(UIEvent.OnMovieClick(movie))
            },
            onViewInflated = {
                viewForTransition = it
                it.transitionName = MainActivity.MOVIE_POSTER_TRANSITION
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewModel.viewState.observe(viewLifecycleOwner, ::render)
    }

    private fun initAdapter() {
        binding.moviesListRecyclerView.apply {
            setAdapterAndCleanupOnDetachFromWindow(moviesAdapter)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun render(viewState: ViewState) {

        setDataToAdapter(viewState)

        updateProgressBar(viewState)

        renderError(viewState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        movie?.let { outState.putParcelable(BUNDLE_MOVIE_KEY, it) }

    }

    private fun updateProgressBar(viewState: ViewState) {
        binding.progressBar.isVisible = viewState.isLoading
    }

    private fun renderError(viewState: ViewState) {
        viewState.errorMessage?.let {
            Snackbar.make(
                requireContext(),
                binding.moviesListFragment,
                it,
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(R.string.retry) {
                    viewModel.processUiEvent(UIEvent.GetMovies)
                }
                .show()
        }
    }

    private fun setDataToAdapter(viewState: ViewState) {
        moviesAdapter.setData(viewState.movies)
    }

    val sharedView
        get() = viewForTransition

}