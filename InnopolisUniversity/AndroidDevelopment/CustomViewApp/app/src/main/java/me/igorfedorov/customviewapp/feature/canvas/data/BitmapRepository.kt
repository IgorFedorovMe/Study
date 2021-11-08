package me.igorfedorov.customviewapp.feature.canvas.data

import android.graphics.Bitmap
import android.net.Uri

interface BitmapRepository {

    fun saveBitmapWithService(bitmap: Bitmap)

    suspend fun getBitmapFromMediaStore(uri: Uri): Bitmap

}