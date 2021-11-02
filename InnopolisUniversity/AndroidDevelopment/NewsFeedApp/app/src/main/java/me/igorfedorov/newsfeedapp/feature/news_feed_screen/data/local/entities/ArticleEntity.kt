package me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import me.igorfedorov.newsfeedapp.base.data_base.contracts.BookmarksContract

@Entity(
    tableName = BookmarksContract.TABLE_NAME,
    primaryKeys = [
        BookmarksContract.Columns.URL
    ]
)
data class ArticleEntity(
    @ColumnInfo(name = BookmarksContract.Columns.URL)
    val url: String,
    @ColumnInfo(name = BookmarksContract.Columns.AUTHOR)
    val author: String?,
    @ColumnInfo(name = BookmarksContract.Columns.CONTENT)
    val content: String?,
    @ColumnInfo(name = BookmarksContract.Columns.DESCRIPTION)
    val description: String?,
    @ColumnInfo(name = BookmarksContract.Columns.PUBLISHED_AT)
    val publishedAt: String?,
    @ColumnInfo(name = BookmarksContract.Columns.TITLE)
    val title: String?,
    @ColumnInfo(name = BookmarksContract.Columns.URL_TO_IMAGE)
    val urlToImage: String?,
    @ColumnInfo(name = BookmarksContract.Columns.IS_BOOKMARKED)
    val isBookmarked: Boolean
)
