package me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api

import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

interface NewsRepository {

    suspend fun getHeadlinesNews(): List<Article>

    suspend fun addArticleToDB(article: Article)

    suspend fun getArticlesFromDB(): List<Article>

    suspend fun updateArticleInDB(article: Article)

    suspend fun deleteArticleFromDB(article: Article)

}