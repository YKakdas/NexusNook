package moadgara.base.util

import android.content.Context
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.request.CachePolicy

object CoilUtil {
    private lateinit var imageLoader: ImageLoader

    fun getCachedCoilImageLoader(context: Context): ImageLoader {
        return if (::imageLoader.isInitialized) {
            imageLoader
        } else {
            ImageLoader.Builder(context)
                .crossfade(false)
                .memoryCache {
                    MemoryCache.Builder(context)
                        .maxSizePercent(0.4)
                        .build()
                }
                .respectCacheHeaders(false)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build().also { this.imageLoader = it }
        }

    }
}