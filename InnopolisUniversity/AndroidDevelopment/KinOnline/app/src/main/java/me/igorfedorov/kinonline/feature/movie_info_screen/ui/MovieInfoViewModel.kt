package me.igorfedorov.kinonline.feature.movie_info_screen.ui

import com.github.terrakok.cicerone.Router
import me.igorfedorov.kinonline.base.base_view_model.BaseViewModel
import me.igorfedorov.kinonline.base.base_view_model.Event
import me.igorfedorov.kinonline.base.cicerone_navigation.Screens

class MovieInfoViewModel(
    private val router: Router
) : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState = ViewState()

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UIEvent.OnPlayButtonCLick -> {
                router.navigateTo(Screens.playerFragment(event.movieUrl))
            }
        }
        return null
    }
}