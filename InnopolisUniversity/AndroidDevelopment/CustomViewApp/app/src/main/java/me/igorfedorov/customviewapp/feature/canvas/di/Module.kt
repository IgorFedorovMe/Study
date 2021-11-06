package me.igorfedorov.customviewapp.feature.canvas.di

import me.igorfedorov.customviewapp.feature.canvas.CanvasFragmentViewModel
import me.igorfedorov.customviewapp.feature.canvas.data.BitmapRepository
import me.igorfedorov.customviewapp.feature.canvas.data.BitmapRepositoryImpl
import me.igorfedorov.customviewapp.feature.canvas.domain.CanvasInteractor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val canvasModule = module {

    single<BitmapRepository> {
        BitmapRepositoryImpl(androidApplication().contentResolver)
    }

    single<CanvasInteractor> {
        CanvasInteractor(get<BitmapRepository>())
    }

    viewModel<CanvasFragmentViewModel> {
        CanvasFragmentViewModel(
            canvasInteractor = get<CanvasInteractor>()
        )
    }

}