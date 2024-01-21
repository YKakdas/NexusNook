package moadgara.base.extension

import android.content.res.Resources
import android.util.TypedValue
import moadgara.base.util.ResourceProvider


val Int.toDp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.toPx: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.toPx(resources: Resources): Int = resources.getDimensionPixelSize(this)

val Int.toSp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this.toFloat(), Resources.getSystem().displayMetrics)

fun Int.toSp(resources: Resources): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this.toFloat(), resources.displayMetrics)

fun Int.stringRes(resources: Resources): String = resources.getString(this)

fun Int.stringRes(resourceProvider: ResourceProvider): String = resourceProvider.getString(this)

val Float.toSp: Float get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this, Resources.getSystem().displayMetrics)

fun Float.toSp(resources: Resources): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this, resources.displayMetrics)


val Int?.orZero: Int get() = this ?: 0

fun Int?.orDefault(default: Int): Int = this ?: default

val Float?.orZero: Float get() = this ?: 0f

fun Float?.orDefault(default: Float): Float = this ?: default