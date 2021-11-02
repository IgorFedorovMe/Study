package me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api

import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.model.NewsDTO

class NewsRemoteSource(
    private val api: MainScreenNewsApi
) {

    suspend fun getHeadlinesNews(): NewsDTO = api.getHeadlinesNews()

}