package moadgara.base.extension

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.RawRes
import androidx.core.view.doOnLayout
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import moadgara.base.R
import moadgara.base.util.CoilUtil

fun ImageView.loadGif(@RawRes gifResId: Int, additionalAnimations: List<Int>) {
    this.load(gifResId, getImageLoader(context))

    if (additionalAnimations.isNotEmpty()) {
        this.doOnLayout {
            val animationSet = AnimationSet(true)
            additionalAnimations.forEach {
                animationSet.addAnimation(AnimationUtils.loadAnimation(context, it))
            }
            startAnimation(animationSet)
        }
    }

}

fun ImageView.prefetchThenCycleImagesRepeatedly(imageList: List<String>?, interval: Long) {
    if (imageList.isNullOrEmpty()) return
    var currentIndex = 0
    val handler = Handler(Looper.getMainLooper())
    CoilUtil.prefetchImages(context, imageList) {
        val runnable = object : Runnable {
            override fun run() {
                load(imageList[currentIndex], CoilUtil.getCachedCoilImageLoader(context)) {
                    error(R.drawable.no_image_found)
                    crossfade(true)
                }
                currentIndex = (currentIndex + 1) % imageList.size
                handler.postDelayed(this, interval)
            }
        }
        handler.postDelayed(runnable, interval)
    }
}

private fun getImageLoader(context: Context): ImageLoader = ImageLoader.Builder(context).components {
    if (SDK_INT >= 28) {
        add(ImageDecoderDecoder.Factory())
    } else {
        add(GifDecoder.Factory())
    }
}.build()