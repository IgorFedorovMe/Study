package me.igorfedorov.customviewapp.feature.canvas.domain

import android.graphics.Bitmap
import android.net.Uri
import me.igorfedorov.customviewapp.base.functional.attempt
import me.igorfedorov.customviewapp.feature.canvas.data.BitmapRepository

class CanvasInteractor(
    private val bitmapRepository: BitmapRepository
) {

    suspend fun saveBitmapToMediaStore(bitmap: Bitmap) = attempt {
        bitmapRepository.saveBitmapToMediaStore(bitmap)
    }

    suspend fun getBitmapFromMediaStore(uri: Uri) = attempt {
        bitmapRepository.getBitmapFromMediaStore(uri)
    }

}