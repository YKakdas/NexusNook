package moadgara.base

import android.content.res.TypedArray

inline fun <reified T : Enum<T>> TypedArray.getEnumOrDefault(index: Int, default: T): T {
    getInt(index, -1).let {
        return if (it >= 0) enumValues<T>()[it] else default
    }
}