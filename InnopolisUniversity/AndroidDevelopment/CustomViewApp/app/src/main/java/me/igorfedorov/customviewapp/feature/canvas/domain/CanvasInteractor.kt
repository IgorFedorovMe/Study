package me.igorfedorov.customviewapp.feature.canvas.domain

import android.graphics.Bitmap
import me.igorfedorov.customviewapp.feature.canvas.data.BitmapRepository

class CanvasInteractor(
    private val bitmapRepository: BitmapRepository
) {

    suspend fun saveBitmapToMediaStore(bitmap: Bitmap) =
        bitmapRepository.saveBitmapToMediaStore(bitmap)

}