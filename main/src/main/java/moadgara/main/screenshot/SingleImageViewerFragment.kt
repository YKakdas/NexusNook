package moadgara.main.screenshot

import android.os.Bundle
import android.view.View
import moadgara.base.BaseFragment
import moadgara.base.viewBinding
import moadgara.main.BR
import moadgara.main.R
import moadgara.main.databinding.LayoutSingleImageViewerBinding
import moadgara.uicomponent.overlay.ToolbarFragment
import moadgara.uicomponent.overlay.ToolbarType

class SingleImageViewerFragment : BaseFragment(R.layout.layout_single_image_viewer), ToolbarFragment {

    companion object {
        const val KEY_IMAGE_URL = "image_url"
    }

    private var imageUrl: String? = null

    private val binding by viewBinding(LayoutSingleImageViewerBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageUrl = arguments?.getString(KEY_IMAGE_URL).orEmpty()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageUrl?.let { binding.setVariable(BR.data, it) }
        binding.executePendingBindings()
    }

    override fun getTitle(): String = ""

    override fun showToolbar(): Boolean = false

    override fun onBackPressed(): Boolean = false

    override fun getToolbarType(): ToolbarType = ToolbarType.BACK

}