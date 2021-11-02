package me.igorfedorov.newsfeedapp.base.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.local.dao.ArticleDao
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.local.entities.ArticleEntity


@Database(
    entities = [
        ArticleEntity::class
    ],
    version = BookmarksDatabase.DB_VERSION
)
abstract class BookmarksDatabase : RoomDatabase() {

    companion object {

        const val DB_VERSION = 1
        const val DB_NAME = "bookmarks_database"
    }

    abstract fun bookmarksDao(): ArticleDao

}
