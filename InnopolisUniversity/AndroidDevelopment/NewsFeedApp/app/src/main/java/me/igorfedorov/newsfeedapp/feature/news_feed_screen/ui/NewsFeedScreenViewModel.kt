package me.igorfedorov.newsfeedapp.feature.news_feed_screen.ui

import me.igorfedorov.newsfeedapp.base.base_view_model.BaseViewModel
import me.igorfedorov.newsfeedapp.base.base_view_model.Event
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.NewsFeedInteractor
import me.igorfedorov.newsfeedapp.feature.news_feed_screen.domain.model.Article


class NewsFeedScreenViewModel(
    private val newsFeedInteractor: NewsFeedInteractor
) : BaseViewModel<ViewState>() {

    init {
        processUiEvent(UIEvent.GetCurrentNews)
    }

    override fun initialViewState(): ViewState {
        return ViewState(
            articleList = emptyList(),
            article = null,
            isLoading = false,
            errorMessage = null,
        )
    }

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UIEvent.GetCurrentNews -> {
                processDataEvent(DataEvent.OnLoadDataTrue)
                newsFeedInteractor.getHeadlinesNews().fold(
                    onError = {
                        processDataEvent(DataEvent.ErrorNewsRequest(it.localizedMessage ?: ""))
                    },
                    onSuccess = {
                        processDataEvent(DataEvent.SuccessNewsRequest(it))
                    }
                )
                processDataEvent(DataEvent.OnLoadDataFalse)
            }
            is UIEvent.OnArticleCLick -> {
                return previousState.copy(article = event.article)
            }
            is UIEvent.OnGoBackFromWebView -> {
                return previousState.copy(article = null)
            }
            is DataEvent.SuccessNewsRequest -> {
                return previousState.copy(
                    articleList = event.articleList,
                    errorMessage = null
                )
            }
            is DataEvent.ErrorNewsRequest -> {
                return previousState.copy(
                    errorMessage = event.errorMessage
                )
            }
            is DataEvent.OnLoadDataTrue -> {
                return previousState.copy(
                    isLoading = true
                )
            }
            is DataEvent.OnLoadDataFalse -> {
                return previousState.copy(
                    isLoading = false
                )
            }
        }
        return null
    }

    fun openArticleWebView(article: Article) {
        processUiEvent(UIEvent.OnArticleCLick(article))
    }

    fun closeArticleWebView() {
        processUiEvent(UIEvent.OnGoBackFromWebView)
    }
}