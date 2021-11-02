package me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model

/*@Entity(
    tableName = ArticlesContract.TABLE_NAME,
    primaryKeys = [
        ArticlesContract.Columns.URL
    ],

    )*/
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String,
    val urlToImage: String?
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
            urlToImage = ""
        )
    }
}
