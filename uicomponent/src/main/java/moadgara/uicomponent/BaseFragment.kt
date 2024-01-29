package moadgara.uicomponent

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.util.MappingUtil.mapValue

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    fun registerRecyclerViewScrollListener(recyclerView: RecyclerView, minHeight: Float, maxHeight: Float) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val toolbar = (requireParentFragment() as? moadgara.uicomponent.overlay.Overlay)
                val verticalOffset = recyclerView.computeVerticalScrollOffset().toFloat()
                val firstVisibleItemPosition =
                    (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0

                val alpha = if (firstVisibleItemPosition > 0) 1f else {
                    mapValue(
                        verticalOffset.coerceIn(minHeight, maxHeight), minHeight, maxHeight, 0f, 1f
                    )
                }
                toolbar?.onToolbarVisibilityChanged(alpha)
            }
        })

    }

}