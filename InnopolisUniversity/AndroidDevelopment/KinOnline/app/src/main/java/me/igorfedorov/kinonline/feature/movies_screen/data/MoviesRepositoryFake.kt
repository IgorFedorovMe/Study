package me.igorfedorov.kinonline.feature.movies_screen.data

import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Genre
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie

class MoviesRepositoryFake : MoviesRepository {

    override suspend fun getAllMovies(): List<Movie> = listOf(
        Movie(
            adult = true,
            genre = listOf(Genre("XXX")),
            id = 1,
            originalLanguage = "en",
            originalTitle = "Asses on Fire",
            overview = "Noice",
            popularity = 10.0,
            posterUrl = "AssesOnFirePoster.url",
            releaseDate = "6.9.1969",
            title = "Жопы в огне",
            video = "xxx-asses-on-fire.mp4",
            voteAverage = 10.0,
            voteCount = 1
        ),
        Movie(
            adult = false,
            genre = listOf(Genre("For children")),
            id = 2,
            originalLanguage = "en",
            originalTitle = "Rainbow Unicorn Butterfly Cat",
            overview = "Say whaaaaat",
            popularity = 100.0,
            posterUrl = "RainbowUBCat.url",
            releaseDate = "01.10.2019",
            title = "Радужно-Едиорожная-Бабочковая Кошка",
            video = "rainbow-unicorn-butterfly-cat.mp4",
            voteAverage = 100.0,
            voteCount = 10000
        )
    )
}