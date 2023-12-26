package moadgara.base.util

import android.content.Context
import android.widget.LinearLayout
import android.widget.Space

object ViewUtil {
    fun createSpace(context: Context, width: Int = -1, height: Int = -1) =
        Space(context).also { it.layoutParams = LinearLayout.LayoutParams(width, height) }
}