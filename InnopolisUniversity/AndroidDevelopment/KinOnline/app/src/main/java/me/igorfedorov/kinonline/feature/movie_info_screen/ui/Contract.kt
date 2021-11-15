package me.igorfedorov.kinonline.feature.movie_info_screen.ui

import me.igorfedorov.kinonline.base.base_view_model.Event

class ViewState()

sealed class UIEvent() : Event {
    data class OnPlayButtonCLick(val movieUrl: String) : UIEvent()
}