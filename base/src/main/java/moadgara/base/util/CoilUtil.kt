package moadgara.base.util

import android.content.Context
import coil.ImageLoader
import coil.request.CachePolicy

object CoilUtil {
    fun getCachedCoilImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
          .crossfade(true)
          .memoryCachePolicy(CachePolicy.ENABLED)
          .diskCachePolicy(CachePolicy.ENABLED)
          .build()
    }
}