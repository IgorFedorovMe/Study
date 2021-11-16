package me.igorfedorov.kinonline.feature.movie_info_screen.ui

import android.view.animation.Animation
import me.igorfedorov.kinonline.base.base_view_model.Event
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie

data class ViewState(
    val animation: Animation?
) {
    val isAnimationViewVisible = animation != null
}

sealed class UIEvent() : Event {
    data class OnPlayButtonCLick(val animation: Animation?) : UIEvent()
    data class NavigateToVideoPlayerScreen(val movie: Movie) : UIEvent()
}