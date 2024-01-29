package moadgara.base.util

import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.core.view.doOnLayout
import androidx.core.view.drawToBitmap

object ScreenCaptureUtil {
    fun captureScreen(window: Window, captureResult: ((Bitmap?) -> Unit)? = null) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            captureResult?.invoke(window.decorView.rootView.drawToBitmap(Bitmap.Config.ARGB_8888))
        } else {
            captureScreenWithPixelCopy(window, captureResult)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun captureScreenWithPixelCopy(window: Window, captureResult: ((Bitmap?) -> Unit)?) {
        val view = window.decorView.rootView
        view.doOnLayout {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val outLocation = IntArray(2)
            view.getLocationInWindow(outLocation)

            val viewX = outLocation[0]
            val viewY = outLocation[1]

            val viewRect = Rect(viewX, viewY, viewX + view.width, viewY + view.height)

            PixelCopy.request(
                window, viewRect, bitmap, { copyResult ->
                    if (copyResult == PixelCopy.SUCCESS) {
                        captureResult?.invoke(bitmap)
                    }
                }, Handler(Looper.getMainLooper())
            )
        }
    }
}