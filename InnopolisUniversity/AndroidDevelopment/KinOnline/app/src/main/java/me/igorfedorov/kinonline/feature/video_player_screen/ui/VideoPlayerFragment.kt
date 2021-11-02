package me.igorfedorov.kinonline.feature.video_player_screen.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.exoplayer2.util.Util
import me.igorfedorov.kinonline.R
import me.igorfedorov.kinonline.databinding.FragmentVideoPlayerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoPlayerFragment : Fragment(R.layout.fragment_video_player) {

    companion object {

        private const val URL_KEY = "URL_KEY"

        fun newInstance(url: String) = VideoPlayerFragment().apply {
            arguments = bundleOf(Pair(URL_KEY, url))
        }
    }

    private val url: String by lazy {
        requireArguments().getString(URL_KEY)!!
    }

    private val binding: FragmentVideoPlayerBinding by viewBinding(FragmentVideoPlayerBinding::bind)

    private val viewModel: VideoPlayerViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        hideSystemUi()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if ((Util.SDK_INT < 24)) {
            initializePlayer()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.videoPlayerView.apply {
            player = viewModel.exoPlayer
            viewModel.processUiEvent(UIEvent.OnGetUrlFromBundle(url))
        }
    }

    private fun initializePlayer() {
        viewModel.initializePlayer()
    }

    private fun releasePlayer() {
        viewModel.releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        showSystemUi()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        showSystemUi()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
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
