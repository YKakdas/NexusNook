package moadgara.uicomponent.glide

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.util.FixedPreloadSizeProvider
import moadgara.uicomponent.R
import timber.log.Timber

object GlideUtil {
    fun getRequestBuilder(
        fragment: Fragment,
        url: String,
        width: Int,
        height: Int
    ): RequestBuilder<*> {
        return Glide.with(fragment)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.no_image_found)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .override(width, height)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    fun getRecyclerViewPreloader(
        fragment: Fragment,
        imageUrls: List<String>,
        width: Int,
        height: Int
    ): RecyclerViewPreloader<String> {
        val sizeProvider = FixedPreloadSizeProvider<String>(width, height)
        val modelProvider =
            ImagePreloadModelProvider(fragment, imageUrls, width, height)
        return RecyclerViewPreloader(Glide.with(fragment), modelProvider, sizeProvider, 20)
    }

    fun preloadImages(
        fragment: Fragment,
        imageUrls: List<String>,
        width: Int,
        height: Int
    ) {
        imageUrls.forEach { preloadImage(fragment, it, width, height) }
    }

    private fun preloadImage(
        fragment: Fragment,
        url: String,
        width: Int,
        height: Int
    ) {
        Glide.with(fragment)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .preload(width, height)
    }
}