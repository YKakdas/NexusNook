package moadgara.uicomponent.overlay

interface Overlay {

    fun registerScreenCaptureRequest(screenCaptureRequest: ScreenCaptureRequest)

    fun onToolbarVisibilityChanged(visibility: Float)

    fun backPress()
}