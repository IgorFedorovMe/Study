package me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class SourceDTO(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = ""
)