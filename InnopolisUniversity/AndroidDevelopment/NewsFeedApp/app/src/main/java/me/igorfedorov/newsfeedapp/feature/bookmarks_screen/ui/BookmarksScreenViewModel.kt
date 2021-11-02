package me.igorfedorov.newsfeedapp.feature.bookmarks_screen.ui

import me.igorfedorov.newsfeedapp.base.base_view_model.BaseViewModel
import me.igorfedorov.newsfeedapp.base.base_view_model.Event
import me.igorfedorov.newsfeedapp.feature.bookmarks_screen.domain.BookmarksInteractor
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article

class BookmarksScreenViewModel(
    private val bookmarksInteractor: BookmarksInteractor
) : BaseViewModel<ViewState>() {

    init {
        processUiEvent(UIEvent.GetCurrentBookmarks)
    }

    override fun initialViewState() = ViewState(
        articles = emptyList(),
        null
    )

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UIEvent.GetCurrentBookmarks -> {
                processUiEvent(DataEvent.OnLoadData)
                bookmarksInteractor.read().fold(
                    onError = {
                        processDataEvent(DataEvent.ErrorBookmarksRequest(it.localizedMessage ?: ""))
                    },
                    onSuccess = {
                        processDataEvent(DataEvent.SuccessBookmarksRequest(it))
                    }
                )
            }
            is DataEvent.RemoveFromBookmarks -> {
                bookmarksInteractor.update(event.article.copy(isBookmarked = false)).fold(
                    onError = {
                        processDataEvent(DataEvent.ErrorBookmarksRequest(it.localizedMessage ?: ""))
                    },
                    onSuccess = {
                        processUiEvent(UIEvent.GetCurrentBookmarks)
                    }
                )
            }
            is DataEvent.SuccessBookmarksRequest -> {
                return previousState.copy(articles = event.articles.filter { it.isBookmarked })
            }
            is DataEvent.ErrorBookmarksRequest -> {
                return previousState.copy(errorMessage = event.errorMessage)
            }
        }
        return null
    }

    fun updateUi() {
        processUiEvent(UIEvent.GetCurrentBookmarks)
    }

    fun deleteFromBookmarks(article: Article) {
        processDataEvent(DataEvent.RemoveFromBookmarks(article))
    }
}