package me.igorfedorov.kinonline.feature.movies_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("name")
    val name: String
)