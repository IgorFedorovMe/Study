package me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api

import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.model.toNews
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

class NewsRepositoryImpl(
    private val newsRemoteSource: NewsRemoteSource
) : NewsRepository {
    override suspend fun getHeadlinesNews(): List<Article> {
        return newsRemoteSource.getHeadlinesNews().toNews().articles
    }
}

