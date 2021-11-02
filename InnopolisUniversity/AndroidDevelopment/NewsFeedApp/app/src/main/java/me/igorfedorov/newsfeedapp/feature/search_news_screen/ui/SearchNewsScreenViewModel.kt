package me.igorfedorov.newsfeedapp.feature.search_news_screen.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import me.igorfedorov.newsfeedapp.base.base_view_model.BaseViewModel
import me.igorfedorov.newsfeedapp.base.base_view_model.Event
import me.igorfedorov.newsfeedapp.feature.search_news_screen.domain.SearchNewsInteractor
import me.igorfedorov.newsfeedapp.feature.search_news_screen.ui.utils.SortOrder

class SearchNewsScreenViewModel(
    private val searchNewsInteractor: SearchNewsInteractor
) : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState = ViewState(
        articles = emptyList(),
        searchText = "",
        searchSortOrder = SortOrder.PUBLISHED_AT,
        errorMessage = null
    )

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UIEvent.SearchArticles -> {
                combine(event.searchTextFlow, event.sortOrder) { search, order ->
                    search to order
                }
                    .debounce(500L)
                    .mapLatest { searchToOrder ->
                        searchNewsInteractor.getArticlesBySearchAndOrder(
                            searchToOrder.first,
                            searchToOrder.second
                        ).fold(
                            onError = { error ->
                                processDataEvent(
                                    DataEvent.ErrorResponse(
                                        error.localizedMessage ?: ""
                                    )
                                )
                            },
                            onSuccess = { articles ->
                                processDataEvent(DataEvent.SuccessResponse(articles))
                            }
                        )
                    }
                    .launchIn(viewModelScope)
            }
            is DataEvent.SuccessResponse -> {
                return previousState.copy(articles = event.articles, errorMessage = null)
            }
            is DataEvent.ErrorResponse -> {
                return previousState.copy(articles = emptyList(), errorMessage = event.errorMessage)
            }
        }
        return null
    }

}