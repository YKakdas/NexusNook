package moadgara.base.extension

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.RawRes
import androidx.core.view.doOnLayout
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load

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

private fun getImageLoader(context: Context): ImageLoader = ImageLoader.Builder(context).components {
    if (SDK_INT >= 28) {
        add(ImageDecoderDecoder.Factory())
    } else {
        add(GifDecoder.Factory())
    }
}.build()