package me.igorfedorov.kinonline.feature.movies_screen.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val adult: Boolean,
    val genre: List<Genre>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterUrl: String,
    val releaseDate: String,
    val title: String,
    val video: String,
    val voteAverage: Double,
    val voteCount: Int
) : Parcelable
