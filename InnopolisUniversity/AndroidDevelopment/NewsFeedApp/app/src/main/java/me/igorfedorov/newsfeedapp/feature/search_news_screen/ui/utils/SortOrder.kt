package me.igorfedorov.newsfeedapp.feature.search_news_screen.ui.utils

enum class SortOrder(val spinnerName: String, val apiName: String) {
    PUBLISHED_AT(spinnerName = "PUBLISHED AT", apiName = "publishedAt"),
    RELEVANCY(spinnerName = "POPULARITY", apiName = "relevancy"),
    POPULARITY(spinnerName = "RELEVANCY", apiName = "popularity"),
}