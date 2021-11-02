package me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api

import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.model.NewsDTO
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface MainScreenNewsApi {

    /*
    *   News Api Documentation:
    *   https://newsapi.org/docs/
    */

    private val supportedCountries: List<String>
        get() = listOf(
            "ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz", "de",
            "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in", "it", "jp", "kr", "lt",
            "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs", "ru",
            "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za",
        )

    @GET("v2/top-headlines")
    suspend fun getHeadlinesNews(
        @Query("country") country: String = if (supportedCountries.contains(Locale.getDefault().country.lowercase())) Locale.getDefault().country else "us",
        @Query("category") category: String = "",
        @Query("sources") sources: String = "",
        @Query("q") query: String = "",
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1,
    ): NewsDTO

}