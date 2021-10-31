package me.igorfedorov.kinonline.feature.movies_screen.ui

import me.igorfedorov.kinonline.base.base_view_model.Event
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie

data class ViewState(
    val movies: List<Movie>,
    val errorMessage: String?,
) {
    val isLoading = movies.isEmpty() && errorMessage == null
}

sealed class UIEvent() : Event {
    object GetMovies : UIEvent()
    data class OnMovieClick(val movie: Movie) : UIEvent()
}

sealed class DataEvent() : Event {
    object ResetViewState : DataEvent()
    data class SuccessMoviesRequest(val movies: List<Movie>) : DataEvent()
    data class ErrorMoviesRequest(val errorMessage: String) : DataEvent()
}

