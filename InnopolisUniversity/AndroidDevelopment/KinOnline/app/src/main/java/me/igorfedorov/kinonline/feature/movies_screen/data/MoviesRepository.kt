package me.igorfedorov.kinonline.feature.movies_screen.data

import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie

interface MoviesRepository {

    suspend fun getAllMovies(): List<Movie>

}