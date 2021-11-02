package me.igorfedorov.newsfeedapp.feature.bookmarks_screen.domain

import me.igorfedorov.newsfeedapp.base.utils.attempt
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.NewsRepository
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

class BookmarksInteractor(
    private val newsRepository: NewsRepository
) {

    suspend fun create(article: Article) = attempt {
        newsRepository.addArticleToDB(article)
    }

    suspend fun read() = attempt {
        newsRepository.getArticlesFromDB()
    }

    suspend fun update(article: Article) = attempt {
        newsRepository.updateArticleInDB(article)
    }

    suspend fun delete(article: Article) = attempt {
        newsRepository.deleteArticleFromDB(article)
    }
}
