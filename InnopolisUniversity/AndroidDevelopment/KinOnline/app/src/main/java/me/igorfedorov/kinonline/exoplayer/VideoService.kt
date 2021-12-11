package me.igorfedorov.kinonline.exoplayer

import android.app.PendingIntent
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import kotlinx.coroutines.*
import me.igorfedorov.kinonline.base.utils.Constants.MEDIA_ROOT_ID
import me.igorfedorov.kinonline.base.utils.Constants.NETWORK_ERROR
import me.igorfedorov.kinonline.base.utils.Constants.VIDEO_FILE_KEY
import me.igorfedorov.kinonline.base.utils.Constants.VIDEO_SERVICE_TAG
import me.igorfedorov.kinonline.exoplayer.callbacks.VideoPlaybackPreparer
import me.igorfedorov.kinonline.exoplayer.callbacks.VideoPlayerListener
import me.igorfedorov.kinonline.exoplayer.callbacks.VideoPlayerNotificationListener
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie
import org.koin.android.ext.android.inject

class VideoService : MediaBrowserServiceCompat() {

    companion object {
        var currentVideoDuration = 0L
            private set
    }

    val exoPlayer by inject<ExoPlayer>()

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

        val videoPlaybackPreparer = VideoPlaybackPreparer(videoSource) {
            currentPlayingVideo = it
        }

        mediaSessionConnector = MediaSessionConnector(mediaSession).apply {
            setPlaybackPreparer(videoPlaybackPreparer)
            setPlayer(exoPlayer)
        }

        videoPlayerListener = VideoPlayerListener(this)

        exoPlayer.addListener(videoPlayerListener)

        videoNotificationManager.showNotification(exoPlayer)

    }

    override fun onBind(intent: Intent?): IBinder {
        intent?.getParcelableExtra<Movie>(VIDEO_FILE_KEY)?.let { movie ->
            preparePlayer(movie, true)
        }
        return VideoServiceBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        stopSelf()
        videoNotificationManager.hideNotification()
        return super.onUnbind(intent)
    }

    private fun preparePlayer(movie: Movie, playNow: Boolean) {
        exoPlayer.apply {
            setMediaItem(MediaItem.fromUri(movie.video))
            prepare()
            playWhenReady = playNow
        }
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        return BrowserRoot(MEDIA_ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        when (parentId) {
            MEDIA_ROOT_ID -> {
                val resultSent = videoSource.whenReady { isInitialized ->
                    if (isInitialized) {
                        result.sendResult(videoSource.asMediaItems())
                        if (!isPlayerInitialized && videoSource.movies.isNotEmpty()) {
                            isPlayerInitialized = true
                        }
                    } else {
                        mediaSession.sendSessionEvent(NETWORK_ERROR, null)
                        result.sendResult(null)
                    }
                }
                if (!resultSent) {
                    result.detach()
                }
            }
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        exoPlayer.removeListener(videoPlayerListener)
        exoPlayer.release()
    }

    inner class VideoServiceBinder : Binder() {
        fun getService() = this@VideoService
    }
}