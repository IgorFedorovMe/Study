package me.igorfedorov.customviewapp.service

import android.app.Service
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.IBinder
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import kotlinx.coroutines.*
import me.igorfedorov.customviewapp.base.utils.minSdk29
import me.igorfedorov.customviewapp.feature.canvas.data.BitmapRepositoryImpl.Companion.BUNDLE_BITMAP_BYTE_ARRAY

class SaveImageService : Service() {

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val byteArray = intent?.getByteArrayExtra(BUNDLE_BITMAP_BYTE_ARRAY)
        val bitmap = byteArray?.let { BitmapFactory.decodeByteArray(byteArray, 0, it.size) }

        if (bitmap != null) {
            serviceScope.launch {
                saveBitmapToStorage(bitmap)
            }
        }

        return START_STICKY
    }

    private fun saveBitmapToStorage(bitmap: Bitmap) {
        val imageUri = saveImageDetails()
        saveImage(bitmap, imageUri)
        makeImageVisible(imageUri)
    }

    private fun saveImage(bitmap: Bitmap, uri: Uri) {
        contentResolver.openOutputStream(uri)?.use { outputStream ->
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
        contentResolver.update(imageUri, imageDetails, null, null)
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
        return contentResolver.insert(imageCollectionUri, imageDetails)!!
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}