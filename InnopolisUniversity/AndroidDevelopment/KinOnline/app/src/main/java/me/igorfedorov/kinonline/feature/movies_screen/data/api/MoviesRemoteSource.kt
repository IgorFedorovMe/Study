package me.igorfedorov.kinonline.feature.movies_screen.data.api

class MoviesRemoteSource(
    private val moviesApi: MoviesApi
) {

    suspend fun getAllMovies() = moviesApi.getAllMovies()

}