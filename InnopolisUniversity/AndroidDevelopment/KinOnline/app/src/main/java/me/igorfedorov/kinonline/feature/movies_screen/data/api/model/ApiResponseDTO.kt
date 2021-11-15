package me.igorfedorov.kinonline.feature.movies_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiResponseDTO(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val moviesDTO: List<MovieDTO>,
    @SerializedName("total_pages")
    val totalPage: Int,
    @SerializedName("total_results")
    val totalResults: Int
)