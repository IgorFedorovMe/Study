package me.igorfedorov.kinonline.exoplayer

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.upstream.DefaultDataSource
import org.koin.android.ext.android.inject

class VideoService : MediaBrowserServiceCompat() {

    private val dataSourceFactory by inject<DefaultDataSource.Factory>()

    private val exoPlayer by inject<ExoPlayer>()

    lateinit var videoNotificationManager: VideoNotificationManager

    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector

    var isForegroundService = false

    private var currentPlayingVideo: MediaMetadataCompat? = null

    private lateinit var videoPlayerListener: VideoPlayerListener

    private var isPlayerInitialized = false

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        TODO("Not yet implemented")
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        TODO("Not yet implemented")
    }


}