package moadgara.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.util.MappingUtil.mapValue

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    fun registerRecyclerViewScrollListener(recyclerView: RecyclerView, minHeight: Float, maxHeight: Float) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val toolbar = (requireParentFragment() as? OnToolbarVisibilityChangedListener)
                val verticalOffset = recyclerView.computeVerticalScrollOffset().toFloat()
                toolbar?.onToolbarVisibilityChanged(
                    mapValue(
                        verticalOffset.coerceIn(minHeight, maxHeight), minHeight, maxHeight, 0f, 1f
                    )
                )
            }
        })
    }

}