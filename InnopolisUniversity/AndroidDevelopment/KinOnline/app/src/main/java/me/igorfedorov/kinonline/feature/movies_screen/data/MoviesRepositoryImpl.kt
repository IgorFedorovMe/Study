package me.igorfedorov.kinonline.feature.movies_screen.data

import me.igorfedorov.kinonline.feature.movies_screen.data.api.MoviesRemoteSource
import me.igorfedorov.kinonline.feature.movies_screen.data.api.model.toMovie
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie

class MoviesRepositoryImpl(
    private val moviesRemoteSource: MoviesRemoteSource
) : MoviesRepository {

    override suspend fun getAllMovies(): List<Movie> {
        return moviesRemoteSource.getAllMovies().moviesDTO.map { it.toMovie() }
    }

}