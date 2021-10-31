package me.igorfedorov.kinonline.feature.movies_screen.ui

import com.github.terrakok.cicerone.Router
import me.igorfedorov.kinonline.base.base_view_model.BaseViewModel
import me.igorfedorov.kinonline.base.base_view_model.Event
import me.igorfedorov.kinonline.base.cicerone_navigation.Screens
import me.igorfedorov.kinonline.feature.movies_screen.domain.MoviesInteractor

class MoviesListViewModel(
    private val moviesInteractor: MoviesInteractor,
    private val router: Router
) : BaseViewModel<ViewState>() {

    init {
        processUiEvent(UIEvent.GetMovies)
    }

    override fun initialViewState(): ViewState = ViewState(
        movies = emptyList(),
        errorMessage = null
    )

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UIEvent.GetMovies -> {
                processDataEvent(DataEvent.ResetViewState)
                moviesInteractor.getAllMovies().fold(
                    onError = {
                        processDataEvent(DataEvent.ErrorMoviesRequest(it.localizedMessage ?: ""))
                    },
                    onSuccess = {
                        processDataEvent(DataEvent.SuccessMoviesRequest(it))
                    }
                )
            }
            is UIEvent.OnMovieClick -> {
                router.navigateTo(Screens.MovieInfo(event.movie))
            }
            is DataEvent.SuccessMoviesRequest -> {
                return previousState.copy(movies = event.movies)
            }
            is DataEvent.ErrorMoviesRequest -> {
                return previousState.copy(errorMessage = event.errorMessage, movies = emptyList())
            }
            is DataEvent.ResetViewState -> {
                return previousState.copy(movies = emptyList(), errorMessage = null)
            }
        }
        return null
    }

}