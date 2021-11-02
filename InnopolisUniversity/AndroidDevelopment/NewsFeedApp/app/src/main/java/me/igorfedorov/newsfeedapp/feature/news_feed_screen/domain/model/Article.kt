package me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model

data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String,
    val urlToImage: String?,
    var isBookmarked: Boolean
) {

    companion object {
        val empty = Article(
            author = "",
            content = "",
            description = "",
            publishedAt = "",
            source = Source.empty,
            title = "",
            url = "",
            urlToImage = "",
            isBookmarked = false
        )
    }
}
