package moadgara.base.extension

import android.os.Build
import android.view.WindowInsets
import moadgara.base.util.PaddingHolder

@Suppress("DEPRECATION")
fun WindowInsets.toPaddingHolder(): PaddingHolder {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val insets = getInsets(WindowInsets.Type.systemBars())
        PaddingHolder(insets.left, insets.top, insets.right, insets.bottom)
    } else {
        PaddingHolder(
            systemWindowInsetLeft,
            systemWindowInsetTop,
            systemWindowInsetRight,
            systemWindowInsetBottom
        )
    }
}
