package me.igorfedorov.newsfeedapp.feature.search_news_screen.ui

import kotlinx.coroutines.flow.Flow
import me.igorfedorov.newsfeedapp.base.base_view_model.Event
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article
import me.igorfedorov.newsfeedapp.feature.search_news_screen.ui.utils.SortOrder

data class ViewState(
    val articles: List<Article>,
    val searchText: String,
    val searchSortOrder: SortOrder,
    val errorMessage: String?
) {
    val isInErrorState = !errorMessage.isNullOrEmpty()
    val isInLoadingState = articles.isEmpty() || isInErrorState
}

sealed class UIEvent() : Event {
    data class SearchArticles(
        val searchTextFlow: Flow<String>,
        val sortOrder: Flow<String>
    ) : UIEvent()
}

sealed class DataEvent() : Event {
    object OnLoadData : DataEvent()
    data class SuccessResponse(val articles: List<Article>) : DataEvent()
    data class ErrorResponse(val errorMessage: String) : DataEvent()
}

