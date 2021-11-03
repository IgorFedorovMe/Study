package me.igorfedorov.customviewapp.feature.canvas.data

import android.graphics.Bitmap

interface BitmapRepository {

    suspend fun saveBitmapToMediaStore(bitmap: Bitmap)

    suspend fun chooseBitmapFromMediaStore(): Bitmap

}