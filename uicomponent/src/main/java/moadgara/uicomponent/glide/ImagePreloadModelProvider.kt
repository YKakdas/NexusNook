package moadgara.uicomponent.glide

import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder

class ImagePreloadModelProvider(
    private val fragment: Fragment,
    private val imageUrls: List<String>,
    private val width: Int,
    private val height: Int
) : ListPreloader.PreloadModelProvider<String> {

    override fun getPreloadItems(position: Int): List<String> {
        return if (position >= imageUrls.size || TextUtils.isEmpty(imageUrls[position])) {
            emptyList()
        } else listOf(imageUrls[position])
    }

    override fun getPreloadRequestBuilder(url: String): RequestBuilder<*> {
        return GlideUtil.getRequestBuilder(fragment, url, width, height)
    }
}