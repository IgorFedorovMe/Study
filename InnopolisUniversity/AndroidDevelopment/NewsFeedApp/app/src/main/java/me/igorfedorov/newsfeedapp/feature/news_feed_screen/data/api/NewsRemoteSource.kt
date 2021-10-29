package me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api

import me.igorfedorov.newsfeedapp.feature.news_feed_screen.data.api.model.NewsDTO
import java.util.*

class NewsRemoteSource(
    private val api: MainScreenNewsApi
) {

    private val currentDeviceLanguage = Locale.getDefault().isO3Country.substring(0, 2)
    private val supportedCountries = listOf(
        "ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz", "de", "eg",
        "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in", "it", "jp", "kr", "lt", "lv", "ma",
        "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs", "ru", "sa", "se", "sg",
        "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za",
    )

    suspend fun getHeadlinesNews(): NewsDTO = api.getHeadlinesNews(
        country = if (supportedCountries.contains(currentDeviceLanguage.lowercase())) currentDeviceLanguage else "us"
    )

}