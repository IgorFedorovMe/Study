package me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.local.entities

import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

fun Article.toArticleEntity() = ArticleEntity(
    url = url,
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    urlToImage = urlToImage,
    isBookmarked = isBookmarked
)

fun ArticleEntity.toArticle() = Article(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    source = null,
    title = title,
    url = url,
    urlToImage = urlToImage,
    isBookmarked = isBookmarked
)
