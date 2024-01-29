package moadgara.base.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import moadgara.base.util.CacheUtil.getImageFromCache

object IntentUtil {

    fun shareImage(context: Context) {
        val image = getImageFromCache(context)
        val contentUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", image)

        if (contentUri != null) {
            val shareIntent = Intent()
            shareIntent.setAction(Intent.ACTION_SEND)
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, context.contentResolver.getType(contentUri))
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
            context.startActivity(Intent.createChooser(shareIntent, "Choose an app"))
        }
    }
}