package me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model

data class Source(
    val id: String?,
    val name: String?
) {

    companion object {
        val empty = Source(
            id = "",
            name = ""
        )
    }
}
