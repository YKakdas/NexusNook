package moadgara.base.extension

import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.sign

fun RecyclerView.setFlingListener(maxVelocity: Int) {
    onFlingListener = object : RecyclerView.OnFlingListener() {
        override fun onFling(velocityX: Int, velocityY: Int): Boolean {
            if (abs(velocityX) > maxVelocity) {
                val newVelocity = maxVelocity * sign(velocityX.toDouble()).toInt()
                fling(newVelocity, velocityY)
                return true
            }
            return false
        }
    }
}