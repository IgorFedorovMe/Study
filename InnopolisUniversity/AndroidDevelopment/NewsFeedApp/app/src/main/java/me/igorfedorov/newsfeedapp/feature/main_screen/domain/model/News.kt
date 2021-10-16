package me.igorfedorov.newsfeedapp.feature.main_screen.domain.model

data class News(
    val articles: List<Article>,
    val status: String?,
    val totalResults: Int
)