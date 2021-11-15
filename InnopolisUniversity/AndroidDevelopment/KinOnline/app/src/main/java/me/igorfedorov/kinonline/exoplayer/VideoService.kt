package me.igorfedorov.kinonline.exoplayer

import android.app.PendingIntent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.upstream.DefaultDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.igorfedorov.kinonline.base.utils.Constants.VIDEO_SERVICE_TAG
import me.igorfedorov.kinonline.exoplayer.callbacks.VideoPlayerListener
import me.igorfedorov.kinonline.exoplayer.callbacks.VideoPlayerNotificationListener
import org.koin.android.ext.android.inject

class VideoService : MediaBrowserServiceCompat() {

    companion object {
        var currentVideoDuration = 0L
            private set
    }

    private val dataSourceFactory by inject<DefaultDataSource.Factory>()

    private val exoPlayer by inject<ExoPlayer>()

    private val videoSource by inject<VideoSource>()

    lateinit var videoNotificationManager: VideoNotificationManager

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)


    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector

    var isForegroundService = false

    private var currentPlayingVideo: MediaMetadataCompat? = null

    private lateinit var videoPlayerListener: VideoPlayerListener

    private var isPlayerInitialized = false

    override fun onCreate() {
        super.onCreate()

        serviceScope.launch {
            videoSource.fetchMediaData()
        }

        val activityIntent = packageManager?.getLaunchIntentForPackage(packageName)?.let { intent ->
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        mediaSession = MediaSessionCompat(this, VIDEO_SERVICE_TAG).apply {
            setSessionActivity(activityIntent)
            isActive = true
        }

        sessionToken = mediaSession.sessionToken

        videoNotificationManager = VideoNotificationManager(
            this,
            mediaSession.sessionToken,
            VideoPlayerNotificationListener(this)
        ) {
            currentVideoDuration = exoPlayer.duration
        }


    }

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