package me.igorfedorov.kinonline.feature.video_player_screen.ui

import me.igorfedorov.kinonline.base.base_view_model.Event

data class ViewState(
    val movieUrl: String
)

sealed class UIEvent() : Event {
    data class OnGetUrlFromBundle(val url: String) : UIEvent()
    object OnPlayButtonClick : UIEvent()
}

sealed class DataEvent() : Event {

}