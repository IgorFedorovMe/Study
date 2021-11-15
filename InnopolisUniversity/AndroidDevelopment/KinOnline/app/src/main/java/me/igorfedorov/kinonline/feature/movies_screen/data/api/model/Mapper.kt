package me.igorfedorov.kinonline.feature.movies_screen.data.api.model

import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Genre
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie

fun MovieDTO.toMovie() = Movie(
    adult = adult,
    genre = genresDTO.map { it.toGenre() },
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterUrl = posterUrl,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun GenreDTO.toGenre() = Genre(
    name = name
)