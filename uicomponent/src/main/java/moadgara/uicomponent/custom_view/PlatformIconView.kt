package moadgara.uicomponent.custom_view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import moadgara.base.extension.toPx
import moadgara.uicomponent.R

@BindingMethods(
    value = [BindingMethod(
        type = PlatformIconView::class, attribute = "platforms", method = "setPlatforms"
    )]
)
class PlatformIconView(context: Context, attributeSet: AttributeSet?, defStyleInt: Int) :
    LinearLayout(context, attributeSet, defStyleInt) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null, 0)

    init {
        orientation = HORIZONTAL
    }

    fun setPlatforms(platforms: List<Int?>) {
        val icons = platforms.filterNotNull().map { platformIconMapper(it) }.distinct().sortedBy { it.first }
        val size = R.dimen.platform_icon_size.toPx(resources)
        icons.forEach {
            post {
                addView(ImageView(context).apply {
                    layoutParams = LayoutParams(size, size).apply { setMargins(0, 0, R.dimen.margin_tiny.toPx(resources), 0) }
                    setImageResource(it.second)
                })
            }
        }
    }

    private fun platformIconMapper(id: Int): Pair<Int, Int> {
        return when (id) {
            4 -> 0 to R.drawable.ic_windows
            1, 14, 80, 186 -> 1 to R.drawable.ic_xbox
            15, 16, 17, 18, 19, 27, 187 -> 2 to R.drawable.ic_ps
            7, 8, 9, 10, 11, 13, 24, 26, 43, 49, 79, 83, 105 -> 3 to R.drawable.ic_nintendo
            3, 5, 41, 55 -> 4 to R.drawable.ic_ios
            21 -> 5 to R.drawable.ic_android
            171 -> 6 to R.drawable.ic_web
            6 -> 7 to R.drawable.ic_linux
            22, 23, 25, 28, 31, 34, 46, 50, 112 -> 8 to R.drawable.ic_atari
            74, 77, 107, 117, 119, 167 -> 9 to R.drawable.ic_sega
            12 -> 10 to R.drawable.ic_neo_geo
            106 -> 11 to R.drawable.ic_dreamcast
            111 -> 12 to R.drawable.ic_3do
            166 -> 13 to R.drawable.ic_amiga
            else -> 0 to 0
        }
    }

}

