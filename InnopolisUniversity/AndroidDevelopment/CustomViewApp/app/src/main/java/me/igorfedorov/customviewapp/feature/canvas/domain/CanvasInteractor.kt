package me.igorfedorov.customviewapp.feature.canvas.domain

import android.graphics.Bitmap
import me.igorfedorov.customviewapp.base.functional.attempt
import me.igorfedorov.customviewapp.feature.canvas.data.BitmapRepository

class CanvasInteractor(
    private val bitmapRepository: BitmapRepository
) {

    suspend fun saveBitmapToMediaStore(bitmap: Bitmap) = attempt {
        bitmapRepository.saveBitmapToMediaStore(bitmap)
    }

}