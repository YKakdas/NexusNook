package moadgara.main.screenshot

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
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

    private val binding by viewBinding(LayoutSingleImageViewerBinding::bind)
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageUrl = arguments?.getString(KEY_IMAGE_URL).orEmpty()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (parentFragment as? Overlay)?.registerScreenCaptureRequest(true, this)
    }

    override fun getTitle(): String = ""

    override fun initialToolbarAlpha(): Float = 0f

    override fun onBackPressed(): Boolean = false

    override fun getToolbarType(): ToolbarType = ToolbarType.BACK
    override fun onScreenCaptureCompleted(bitmap: Bitmap?) {
        if (bitmap != null) {
            BitmapUtil.blurImage(bitmap, requireContext()).let {
                binding.setVariable(BR.backgroundBitmap, it)
            }
        }
        imageUrl?.let { imageUrl -> binding.setVariable(BR.data, imageUrl) }
        binding.executePendingBindings()
    }

}