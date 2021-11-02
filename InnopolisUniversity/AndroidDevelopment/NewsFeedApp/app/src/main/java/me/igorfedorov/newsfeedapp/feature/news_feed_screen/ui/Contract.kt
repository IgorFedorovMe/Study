package me.igorfedorov.newsfeedapp.feature.news_feed_screen.ui

import me.igorfedorov.newsfeedapp.base.base_view_model.Event
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

data class ViewState(
    val articles: List<Article>,
    val article: Article?,
    val errorMessage: String?
) {
    val isInErrorState = errorMessage != null
    val isLoading = articles.isEmpty() && !isInErrorState
}

sealed class UIEvent() : Event {
    object GetArticlesFromDB : UIEvent()
    object OnGoBackFromWebView : UIEvent()
    data class OnArticleCLick(val article: Article) : UIEvent()
    object OnConfigurationChanged : UIEvent()
}

sealed class DataEvent() : Event {
    object GetCurrentNews : UIEvent()
    data class SuccessNewsRequest(val articleList: List<Article>) : DataEvent()
    data class ErrorNewsRequest(val errorMessage: String) : DataEvent()
    data class AddArticleToBookmarks(val article: Article) : DataEvent()
    data class RemoveArticleFromBookmarks(val article: Article) : DataEvent()
}