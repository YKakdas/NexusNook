package moadgara.main.screenshot

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import moadgara.base.extension.getAny
import moadgara.base.extension.toPx
import moadgara.base.util.BitmapUtil
import moadgara.base.viewBinding
import moadgara.main.BR
import moadgara.main.R
import moadgara.main.databinding.LayoutSingleImageViewerBinding
import moadgara.uicomponent.BaseFragment
import moadgara.uicomponent.overlay.Overlay
import moadgara.uicomponent.overlay.ScreenCaptureRequest
import moadgara.uicomponent.overlay.ToolbarFragment
import moadgara.uicomponent.overlay.ToolbarType

class SingleImageViewerFragment : BaseFragment(R.layout.layout_single_image_viewer), ToolbarFragment, ScreenCaptureRequest {

    companion object {
        const val KEY_IMAGE_URL = "image_url"
    }

    private var imageUrl: String? = null
    private var isPortrait = true
    private val binding by viewBinding(LayoutSingleImageViewerBinding::bind)
    private var overlay: Overlay? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageUrl = arguments?.getString(KEY_IMAGE_URL).orEmpty()
        overlay = parentFragment as? Overlay
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        overlay?.registerScreenCaptureRequest(this)
    }

    override fun getTitle(): String = ""

    override fun initialToolbarAlpha(): Float = 0f

    override fun onBackPressed(): Boolean = false

    override fun getToolbarType(): ToolbarType = ToolbarType.BACK

    override fun showToolbar(): Boolean = false

    override fun onScreenCaptureCompleted(bitmap: Bitmap?) {
        if (bitmap != null) {
            BitmapUtil.blurImage(bitmap, requireContext()).let {
                binding.setVariable(BR.backgroundBitmap, it)
            }
        }
        imageUrl?.let { imageUrl -> binding.setVariable(BR.data, imageUrl) }
        binding.executePendingBindings()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateLayoutParameters(newConfig.orientation)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun toggleOrientation() {
        if (isPortrait) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        isPortrait = !isPortrait;
    }

    private fun updateLayoutParameters(orientation: Int) {
        binding.image.run {
            val layoutParams = layoutParams as FrameLayout.LayoutParams
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                scaleType = ImageView.ScaleType.FIT_XY
            } else {
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                layoutParams.height = moadgara.uicomponent.R.dimen.game_detail_screenshot_image_height.toPx(resources)
                scaleType = ImageView.ScaleType.FIT_CENTER
            }
            this.layoutParams = layoutParams
        }
    }

}