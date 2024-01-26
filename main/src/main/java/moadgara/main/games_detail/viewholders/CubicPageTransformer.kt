package moadgara.main.games_detail.viewholders

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs


class CubicPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        if (position < -1 || position > 1) {    // (-Infinity,-1) or (1, Infinity) Item not visible, make it transparent
            page.alpha = 0f
        } else if (position <= 0) {    // [-1,0] item is being slided to left or slided from left to center
            page.alpha = (1 - abs(position)).coerceIn(0f, 1f)
            page.translationZ  = (1 - abs(position)) * 30
            page.pivotX = page.width.toFloat()
            page.rotationY = -90 * abs(position)
        } else if (position <= 1) {    // (0,1] item is being slided to right or slided from right to center
            page.alpha = (1 - abs(position)).coerceIn(0f, 1f)
            page.pivotX = 0f
            page.rotationY = 90 * abs(position)
        }
    }
}