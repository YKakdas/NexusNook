package moadgara.uicomponent.overlay

interface Overlay {
    fun registerScreenCaptureRequest(disableToolbar: Boolean = false, screenCaptureRequest: ScreenCaptureRequest)

    fun onToolbarVisibilityChanged(visibility: Float)
}