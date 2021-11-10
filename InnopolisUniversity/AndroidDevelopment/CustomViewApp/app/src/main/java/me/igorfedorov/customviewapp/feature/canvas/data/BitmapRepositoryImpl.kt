package me.igorfedorov.customviewapp.feature.canvas.data

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import me.igorfedorov.customviewapp.base.utils.minSdk29
import me.igorfedorov.customviewapp.service.SaveImageService
import java.io.ByteArrayOutputStream

class BitmapRepositoryImpl(
    private val context: Context
) : BitmapRepository {

    companion object {
        const val BUNDLE_BITMAP_BYTE_ARRAY = "BUNDLE_BITMAP_BYTE_ARRAY"
    }

    override fun saveBitmapWithService(bitmap: Bitmap) {
        ByteArrayOutputStream().use { stream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            val intent = Intent(context, SaveImageService::class.java).apply {
                putExtra(BUNDLE_BITMAP_BYTE_ARRAY, byteArray)
            }
            context.startService(intent)
        }
    }

    override suspend fun getBitmapFromMediaStore(uri: Uri): Bitmap {
        return if (minSdk29()) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }

    /*private var bitmapObserver: ContentObserver? = null

    fun observeVideos(onChange: () -> Unit) {
        bitmapObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                onChange()
            }
        }
        context.contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            bitmapObserver ?: throw IllegalStateException("Observer not initialized")
        )
    }

    fun unregisterVideoObserver() {
        bitmapObserver?.let { context.contentResolver.unregisterContentObserver(it) }
    }*/
}