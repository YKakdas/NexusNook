package moadgara.base.extension

import android.view.View
import android.view.ViewGroup

fun ViewGroup.addViewCheckIfExists(viewToAdd: View, index: Int? = null) {
    if (indexOfChild(viewToAdd) != -1) {
        removeView(viewToAdd)
    }
    if (index != null) {
        addView(viewToAdd, index)
    } else {
        addView(viewToAdd)
    }
}