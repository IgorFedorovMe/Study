package me.igorfedorov.kinonline.feature.movies_screen.ui

import me.igorfedorov.kinonline.base.base_view_model.Event
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie

data class ViewState(
    val movies: List<Movie>,
    val errorMessage: String?,
) {
    val isInErrorState = !errorMessage.isNullOrEmpty()
    val isLoading = movies.isEmpty() || isInErrorState
}

sealed class UIEvent() : Event {
    object OnGetMovies : UIEvent()
    data class OnMovieClick(val movie: Movie) : UIEvent()
}

sealed class DataEvent() : Event {
    data class SuccessMoviesRequest(val movies: List<Movie>) : DataEvent()
    data class ErrorMoviesRequest(val errorMessage: String) : DataEvent()
}