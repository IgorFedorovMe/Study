package me.igorfedorov.kinonline.feature.movie_info_screen.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import me.igorfedorov.kinonline.R
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

        binding.apply {
            moviePosterImageView.loadImage(movie.posterUrl)
            titleTextView.text = movie.title
            descriptionTextView.text = movie.overview
            playMovieFab.setThrottledClickListener {
                viewModel.processUiEvent(UIEvent.OnPlayButtonCLick(movie.video))
            }
        }
    }
}