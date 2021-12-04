package me.igorfedorov.kinonline.feature.video_player_screen.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import me.igorfedorov.kinonline.R
import me.igorfedorov.kinonline.base.utils.Constants.VIDEO_FILE_KEY
import me.igorfedorov.kinonline.databinding.FragmentVideoPlayerBinding
import me.igorfedorov.kinonline.exoplayer.VideoService
import me.igorfedorov.kinonline.feature.movies_screen.domain.model.Movie

class VideoPlayerFragment : Fragment(R.layout.fragment_video_player) {

    companion object {

        private const val MOVIE_KEY = "URL_KEY"

        fun newInstance(movie: Movie) = VideoPlayerFragment().apply {
            arguments = bundleOf(Pair(MOVIE_KEY, movie))
        }
    }

    private val movie: Movie by lazy {
        requireArguments().getParcelable(MOVIE_KEY)!!
    }

    private val binding: FragmentVideoPlayerBinding by viewBinding(FragmentVideoPlayerBinding::bind)

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {}

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            when (service) {
                is VideoService.VideoServiceBinder -> {
                    binding.videoPlayerView.player = service.getService().exoPlayer
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        hideSystemUi()
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(requireContext(), VideoService::class.java)
        intent.putExtra(VIDEO_FILE_KEY, movie)
        requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE)
        hideSystemUi()
    }

    override fun onPause() {
        super.onPause()
        showSystemUi()
    }

    override fun onStop() {
        super.onStop()
        showSystemUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unbindService(connection)
    }

    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(
            requireActivity().window,
            binding.videoPlayerView
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(
            requireActivity().window,
            true
        )

        WindowInsetsControllerCompat(
            requireActivity().window,
            binding.videoPlayerView
        ).show(WindowInsetsCompat.Type.systemBars())
    }
}
