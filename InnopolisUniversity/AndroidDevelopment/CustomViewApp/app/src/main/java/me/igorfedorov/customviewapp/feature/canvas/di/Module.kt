package me.igorfedorov.customviewapp.feature.canvas.di

import me.igorfedorov.customviewapp.feature.canvas.CanvasFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val canvasModule = module {

    viewModel<CanvasFragmentViewModel> {
        CanvasFragmentViewModel()
    }


}