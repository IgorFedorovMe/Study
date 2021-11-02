package me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import me.igorfedorov.newsfeedapp.base.data_base.contracts.BookmarksContract
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.local.entities.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertArticle(articleEntity: ArticleEntity)

    @Query("SELECT * FROM ${BookmarksContract.TABLE_NAME}")
    suspend fun getAllArticles(): List<ArticleEntity>

    @Query("SELECT * FROM ${BookmarksContract.TABLE_NAME}")
    fun subscribeToDB(): LiveData<List<ArticleEntity>>

    @Update
    suspend fun updateArticle(articleEntity: ArticleEntity)

    @Delete
    suspend fun deleteArticle(articleEntity: ArticleEntity)

    @Query("DELETE FROM ${BookmarksContract.TABLE_NAME} WHERE ${BookmarksContract.Columns.IS_BOOKMARKED} = '0'")
    suspend fun deleteAllNotBookmarkedArticlesFromDB()

}