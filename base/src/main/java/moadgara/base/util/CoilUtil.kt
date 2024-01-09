package moadgara.base.util

import android.content.Context
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest

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

    fun prefetchImages(
        context: Context,
        imageList: List<String>,
        callback: (() -> Unit)?
    ) {
        val imageLoader = getCachedCoilImageLoader(context)
        var count = 0
        imageList.forEach { imageUrl ->
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .listener(
                    onSuccess = { _, _ ->
                        count++
                        if (count == imageList.size) callback?.invoke()
                    },
                    onError = { _, _ ->
                        count++
                        if (count == imageList.size) callback?.invoke()
                    },
                    onCancel = { _ ->
                        count++
                        if (count == imageList.size) callback?.invoke()
                    })
                .build()
            imageLoader.enqueue(request)
        }
    }
}