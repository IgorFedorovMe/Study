package me.igorfedorov.kinonline.feature.movies_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class MovieDTO(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("genres")
    val genresDTO: List<GenreDTO>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterUrl: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)