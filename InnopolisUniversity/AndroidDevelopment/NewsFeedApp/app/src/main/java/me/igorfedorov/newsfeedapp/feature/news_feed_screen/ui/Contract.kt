package me.igorfedorov.newsfeedapp.feature.news_feed_screen.ui

import me.igorfedorov.newsfeedapp.base.base_vies_model.Event
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

data class ViewState(
    val articleList: List<Article>,
    val isLoading: Boolean,
    val errorMessage: String,
    val isInErrorState: Boolean
)

sealed class UIEvent() : Event {
    object GetCurrentNews : UIEvent()
}

sealed class DataEvent() : Event {
    object OnLoadData : DataEvent()
    data class SuccessNewsRequest(val articleList: List<Article>) : DataEvent()
    data class ErrorNewsRequest(val errorMessage: String) : DataEvent()
}