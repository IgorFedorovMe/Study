package me.igorfedorov.kinonline.feature.movies_screen.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import me.igorfedorov.kinonline.R
import me.igorfedorov.kinonline.base.utils.loadImage
import me.igorfedorov.kinonline.base.utils.setThrottledClickListener
import me.igorfedorov.kinonline.databinding.ItemMovieBinding
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie

class MoviesAdapter(
    onItemClick: (movie: Movie, layoutPosition: Int) -> Unit
) : AsyncListDifferDelegationAdapter<Movie>(MoviesDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(movieAdapterDelegate(onItemClick))
    }

    @SuppressLint("CheckResult")
    private fun movieAdapterDelegate(onItemClick: (movie: Movie, layoutPosition: Int) -> Unit) =
        adapterDelegateViewBinding<Movie, Movie, ItemMovieBinding>(
            { layoutInflater, parent -> ItemMovieBinding.inflate(layoutInflater, parent, false) }
        ) {

            binding.root.setThrottledClickListener {
                onItemClick(item, layoutPosition)
            }

            bind {
                binding.apply {
                    moviePosterImageView.loadImage(item.posterUrl) {
                        centerCrop()
                        placeholder(R.drawable.ic_movies_placeholder)
                    }
                }
            }
        }


    class MoviesDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}