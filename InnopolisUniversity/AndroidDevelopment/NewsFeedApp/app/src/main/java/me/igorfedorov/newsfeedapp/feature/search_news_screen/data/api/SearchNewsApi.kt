package me.igorfedorov.newsfeedapp.feature.search_news_screen.data.api

import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.model.NewsDTO
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchNewsApi {

    /*
    *   News Api Documentation:
    *   https://newsapi.org/docs/
    */

    @GET("v2/everything")
    suspend fun getEverythingNews(
        @Query("q") query: String = "b",
        @Query("qInTitle") queryInTitle: String = "",
        @Query("sources") sources: String = "",
        @Query("domains") domains: String = "",
        @Query("excludeDomains") excludeDomains: String = "",
        @Query("from") from: String = "",
        @Query("to") to: String = "",
        @Query("language") language: String = "",
        @Query("sortBy") sortBy: String = "",
        @Query("pageSize") pageSize: Int = 100,
        @Query("page") page: Int = 1,
    ): NewsDTO

}