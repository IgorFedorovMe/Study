package me.igorfedorov.kinonline.feature.video_player_screen.di

import com.github.terrakok.cicerone.Router
import com.google.android.exoplayer2.ExoPlayer
import me.igorfedorov.kinonline.feature.video_player_screen.ui.VideoPlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val videoPlayerModule = module {

    viewModel<VideoPlayerViewModel> {
        VideoPlayerViewModel(
            router = get<Router>(),
            exoPlayer = get<ExoPlayer>()
        )
    }

}