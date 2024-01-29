package moadgara.base.util

import android.content.Context
import android.graphics.Bitmap
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object CacheUtil {

    fun saveBitmapToCache(context: Context, bitmap: Bitmap): File? {
        val cacheDir = createImageCacheDirectory(context)
        val imageName = "share_screenshot.png"
        return try {
            val imageFile = File(cacheDir, imageName)
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
            imageFile
        } catch (e: IOException) {
            Timber.d("Couldn't write bitmap to the cache!")
            null
        }
    }

    fun getImageFromCache(context: Context): File {
        val imagePath = File(context.cacheDir, "images")
        return File(imagePath, "share_screenshot.png")
    }

    private fun createImageCacheDirectory(context: Context): File {
        val cacheDir = File(context.cacheDir, "images")

        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        return cacheDir
    }

}