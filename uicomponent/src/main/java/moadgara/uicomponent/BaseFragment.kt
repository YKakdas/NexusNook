package moadgara.uicomponent

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.util.MappingUtil.mapValue
import moadgara.uicomponent.overlay.Overlay
import moadgara.uicomponent.overlay.ToolbarFragment
import moadgara.uicomponent.overlay.ToolbarType

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId), ToolbarFragment {

    fun registerRecyclerViewScrollListener(recyclerView: RecyclerView, minHeight: Float, maxHeight: Float, overlay: Overlay?) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val verticalOffset = recyclerView.computeVerticalScrollOffset().toFloat()
                val firstVisibleItemPosition =
                    (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0

                val alpha = if (firstVisibleItemPosition > 0) 1f else {
                    mapValue(
                        verticalOffset.coerceIn(minHeight, maxHeight), minHeight, maxHeight, 0f, 1f
                    )
                }
                overlay?.onToolbarVisibilityChanged(alpha)
            }
        })

    }

    override fun getTitle(): String? = null

    override fun initialToolbarAlpha(): Float = 0f

    override fun onBackPressed(): Boolean = false

    override fun getToolbarType(): ToolbarType = ToolbarType.AUTO

    override fun showToolbar(): Boolean = true
}