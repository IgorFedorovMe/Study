package me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain

import me.igorfedorov.newsfeedapp.base.utils.attempt
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.NewsRepository
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

class NewsFeedInteractor(
    private val newsRepository: NewsRepository
) {

    suspend fun getArticlesFromDB() = attempt {
        newsRepository.getArticlesFromDB()
    }

    suspend fun getHeadlinesNews() = attempt {
        val news = newsRepository.getHeadlinesNews()
        val bookmarks = newsRepository.getArticlesFromDB()
        news.map { article ->
            if (bookmarks.map { bookmarksArticle ->
                    bookmarksArticle.url
                }.contains(article.url)) {
                article.copy(isBookmarked = true)
            } else {
                article.copy(isBookmarked = false)
            }
        }
    }

    fun subscribeToDB() = attempt {
        newsRepository.subscribeToDB()
    }

    suspend fun addArticleToDB(article: Article) = attempt {
        newsRepository.addArticleToDB(article)
    }

    suspend fun addArticleToBookmarks(article: Article) = attempt {
        newsRepository.updateArticleInDB(article.copy(isBookmarked = true))
    }

    suspend fun deleteArticleFromBookmarks(article: Article) = attempt {
        newsRepository.deleteArticleFromDB(article)
    }
}