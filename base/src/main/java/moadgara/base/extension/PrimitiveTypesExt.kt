package moadgara.base.extension

import android.content.res.Resources
import android.util.TypedValue
import moadgara.base.util.ResourceProvider


val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.px(resources: Resources): Int = resources.getDimensionPixelSize(this)

val Int.sp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this.toFloat(), Resources.getSystem().displayMetrics)

fun Int.sp(resources: Resources): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this.toFloat(), resources.displayMetrics)

fun Int.stringRes(resources: Resources): String = resources.getString(this)

fun Int.stringRes(resourceProvider: ResourceProvider): String = resourceProvider.getString(this)

val Float.sp: Float get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this, Resources.getSystem().displayMetrics)

fun Float.sp(resources: Resources): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this, resources.displayMetrics)


val Int?.orZero: Int get() = this ?: 0

fun Int?.orDefault(default: Int): Int = this ?: default

val Float?.orZero: Float get() = this ?: 0f

fun Float?.orDefault(default: Float): Float = this ?: default