package moadgara.base.extension

import android.content.res.Resources

val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.px(resources: Resources): Int = resources.getDimensionPixelSize(this)


fun Int?.orZero(): Int = this ?: 0

fun Int?.orDefault(default: Int): Int = this ?: default