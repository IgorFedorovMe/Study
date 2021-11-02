package me.igorfedorov.newsfeedapp.feature.search_news_screen.data.api

class SearchNewsRemoteSource(
    private val searchNewsApi: SearchNewsApi
) {

    suspend fun getNewsBySearchAndOrder(query: String, sortBy: String) =
        searchNewsApi.getEverythingNews(query = query, sortBy = sortBy)

}