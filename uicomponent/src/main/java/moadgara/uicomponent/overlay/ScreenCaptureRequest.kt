package moadgara.uicomponent.overlay

import android.graphics.Bitmap

interface ScreenCaptureRequest {
    fun onScreenCaptureCompleted(bitmap: Bitmap?)
}