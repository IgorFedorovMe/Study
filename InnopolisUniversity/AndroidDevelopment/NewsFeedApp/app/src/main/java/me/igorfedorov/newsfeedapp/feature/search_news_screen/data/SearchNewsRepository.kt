package me.igorfedorov.newsfeedapp.feature.search_news_screen.data

import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

interface SearchNewsRepository {

    suspend fun getArticlesBySearchAndOrder(search: String, order: String): List<Article>

}