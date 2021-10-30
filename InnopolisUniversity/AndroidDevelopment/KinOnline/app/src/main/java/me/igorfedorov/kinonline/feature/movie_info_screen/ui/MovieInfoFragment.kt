package me.igorfedorov.kinonline.feature.movie_info_screen.ui

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import me.igorfedorov.kinonline.R
import me.igorfedorov.kinonline.base.cicerone_navigation.utils.startAnimation
import me.igorfedorov.kinonline.base.utils.loadImage
import me.igorfedorov.kinonline.base.utils.setThrottledClickListener
import me.igorfedorov.kinonline.databinding.FragmentMovieInfoBinding
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieInfoFragment : Fragment(R.layout.fragment_movie_info) {

    companion object {
        private const val MOVIE_KEY = "MOVIE_KEY"

        fun newInstance(movie: Movie) = MovieInfoFragment().apply {
            arguments = bundleOf(Pair(MOVIE_KEY, movie))
        }
    }

    private val viewModel: MovieInfoViewModel by viewModel()

    private val binding: FragmentMovieInfoBinding by viewBinding(FragmentMovieInfoBinding::bind)

    private val movie: Movie by lazy {
        requireArguments().getParcelable(MOVIE_KEY)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.circle_explosion_anim).apply {
                duration = 500
                interpolator = AccelerateDecelerateInterpolator()
            }

        binding.apply {
            moviePosterImageView.loadImage(movie.posterUrl)
            titleTextView.text = movie.title
            descriptionTextView.text = movie.overview
            playMovieFab.setThrottledClickListener {
                viewModel.processUiEvent(UIEvent.OnPlayButtonCLick(animation = animation))
            }
        }

        viewModel.viewState.observe(viewLifecycleOwner, ::render)
    }

    private fun render(viewState: ViewState) {
        navigateWithAnimation(viewState)
        manageViewVisibility(viewState)
    }

    private fun manageViewVisibility(viewState: ViewState) {
        binding.circle.apply {
            isVisible = viewState.isAnimationViewVisible
            elevation = 20F
        }
    }

    private fun navigateWithAnimation(viewState: ViewState) {
        viewState.animation?.let {
            binding.circle.startAnimation(it) {
                viewModel.processUiEvent(UIEvent.NavigateToVideoPlayerScreen(movieUrl = movie.video))
            }
        }
    }
}