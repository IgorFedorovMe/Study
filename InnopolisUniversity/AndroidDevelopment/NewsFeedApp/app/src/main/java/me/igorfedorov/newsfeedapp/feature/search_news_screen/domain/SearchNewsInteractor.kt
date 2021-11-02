package me.igorfedorov.newsfeedapp.feature.search_news_screen.domain

import me.igorfedorov.newsfeedapp.base.utils.attempt
import me.igorfedorov.newsfeedapp.feature.search_news_screen.data.SearchNewsRepository

class SearchNewsInteractor(
    private val searchNewsRepository: SearchNewsRepository
) {

    suspend fun getArticlesBySearchAndOrder(search: String, order: String) = attempt {
        searchNewsRepository.getArticlesBySearchAndOrder(
            search = search,
            order = order
        )
    }

}