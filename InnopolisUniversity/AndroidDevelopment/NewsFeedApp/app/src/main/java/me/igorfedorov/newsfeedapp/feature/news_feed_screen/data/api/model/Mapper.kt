package me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.model

import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.News
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Source

fun NewsDTO.toNews(): News =
    News(
        articles = articlesDTO.map { it.toArticle() },
        status = status,
        totalResults = totalResults
    )

fun ArticleDTO.toArticle(): Article =
    Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = sourceDTO.toSource(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )

fun SourceDTO.toSource(): Source =
    Source(
        id = id,
        name = name
    )