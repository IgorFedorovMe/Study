package me.igorfedorov.customviewapp.feature.canvas.data

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.igorfedorov.customviewapp.base.utils.minSdk29

class BitmapRepositoryImpl(
    private val context: Context
) : BitmapRepository {

    override suspend fun saveBitmapToMediaStore(bitmap: Bitmap) {
        withContext(Dispatchers.IO) {
            val imageUri = saveImageDetails()
            saveImage(bitmap, imageUri)
            makeImageVisible(imageUri)
        }
    }

    override suspend fun getBitmapFromMediaStore(uri: Uri): Bitmap {
        return if (minSdk29()) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
//        withContext(Dispatchers.IO) {
//            context.contentResolver.query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//            )
//        }
//        return bitmap

    }

    private fun saveImage(bitmap: Bitmap, uri: Uri) {
        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            outputStream.use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        }
    }

    private fun makeImageVisible(imageUri: Uri) {
        if (minSdk29().not()) return

        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.IS_PENDING, 0)
        }
        context.contentResolver.update(imageUri, imageDetails, null, null)
    }

    private fun saveImageDetails(): Uri {
        val volume =
            if (minSdk29()) MediaStore.VOLUME_EXTERNAL_PRIMARY else MediaStore.VOLUME_EXTERNAL

        val imageCollectionUri = MediaStore.Images.Media.getContentUri(volume)
        val fileExtension = ".png"
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
        val name = System.currentTimeMillis().toString() + ".png"
        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, name)
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
            if (minSdk29())
                put(MediaStore.Images.Media.IS_PENDING, 1)
        }
        return context.contentResolver.insert(imageCollectionUri, imageDetails)!!
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