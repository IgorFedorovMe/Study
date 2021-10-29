package me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api

import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

interface NewsRepository {

    suspend fun getHeadlinesNews(): List<Article>

}