package me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model

data class News(
    val articles: List<Article>,
    val status: String?,
    val totalResults: Int
)
