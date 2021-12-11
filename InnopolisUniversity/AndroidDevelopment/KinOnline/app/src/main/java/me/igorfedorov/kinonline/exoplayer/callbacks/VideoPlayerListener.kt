package me.igorfedorov.kinonline.exoplayer.callbacks

import com.google.android.exoplayer2.Player
import me.igorfedorov.kinonline.exoplayer.VideoService

class VideoPlayerListener(
    private val videoService: VideoService
) : Player.Listener {

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == Player.STATE_READY)
            videoService.stopForeground(false)
    }

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)
        if (!playWhenReady)
            videoService.stopForeground(false)
    }
}