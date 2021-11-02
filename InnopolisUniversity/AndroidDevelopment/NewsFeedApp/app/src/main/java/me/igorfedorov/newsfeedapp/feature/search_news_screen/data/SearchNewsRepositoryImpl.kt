package me.igorfedorov.newsfeedapp.feature.search_news_screen.data

import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.model.toNews
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article
import me.igorfedorov.newsfeedapp.feature.search_news_screen.data.api.SearchNewsRemoteSource

class SearchNewsRepositoryImpl(
    private val searchNewsRemoteSource: SearchNewsRemoteSource
) : SearchNewsRepository {

    override suspend fun getArticlesBySearchAndOrder(search: String, order: String): List<Article> =
        searchNewsRemoteSource.getNewsBySearchAndOrder(query = search, sortBy = order)
            .toNews().articles


}