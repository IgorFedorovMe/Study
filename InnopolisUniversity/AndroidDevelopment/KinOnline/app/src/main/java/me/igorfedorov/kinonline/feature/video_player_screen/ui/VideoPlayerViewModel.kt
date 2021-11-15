package me.igorfedorov.kinonline.feature.video_player_screen.ui

import com.github.terrakok.cicerone.Router
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import me.igorfedorov.kinonline.base.base_view_model.BaseViewModel
import me.igorfedorov.kinonline.base.base_view_model.Event

class VideoPlayerViewModel(
    private val router: Router,
    val exoPlayer: ExoPlayer
) : BaseViewModel<ViewState>() {

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    override fun initialViewState() = ViewState(
        movieUrl = ""
    )

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UIEvent.OnGetUrlFromBundle -> {
                exoPlayer.apply {
                    setMediaItem(MediaItem.fromUri(event.url))
                }
            }
            is UIEvent.OnPlayButtonClick -> {

            }
        }
        return null
    }

    fun initializePlayer() {
        exoPlayer.apply {
            playWhenReady = playWhenReady
            seekTo(currentWindow, playbackPosition)
            prepare()
        }
    }
}