package moadgara.base.util

import android.content.res.Resources
import androidx.annotation.DimenRes

object DimensionUtil {
    fun dpToPx(@DimenRes resourceId: Int? = null, value: Int = 0, resources: Resources): Int {
        return if (resourceId != null) {
            resources.getDimensionPixelSize(resourceId)
        } else {
            (value * resources.displayMetrics.density).toInt()
        }
    }

}
