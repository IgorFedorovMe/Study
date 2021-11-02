package me.igorfedorov.newsfeedapp.feature.bookmarks_screen.ui

import me.igorfedorov.newsfeedapp.base.base_view_model.Event
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

data class ViewState(
    val articles: List<Article>,
    val errorMessage: String?
) {

    val isInErrorState = errorMessage != null

}

sealed class UIEvent() : Event {
    object GetCurrentBookmarks : UIEvent()
}

sealed class DataEvent() : Event {
    object OnLoadData : DataEvent()
    data class SuccessBookmarksRequest(val articles: List<Article>) : DataEvent()
    data class ErrorBookmarksRequest(val errorMessage: String) : DataEvent()
    data class RemoveFromBookmarks(val article: Article) : DataEvent()
}

