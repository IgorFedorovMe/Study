package me.igorfedorov.kinonline.feature.movies_screen.domain

import me.igorfedorov.kinonline.base.functional.attempt
import me.igorfedorov.kinonline.feature.movies_screen.data.MoviesRepository

class MoviesInteractor(
    private val moviesRepository: MoviesRepository
) {

    suspend fun getAllMovies() = attempt {
        moviesRepository.getAllMovies()
    }

}