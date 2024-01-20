package moadgara.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.util.MappingUtil.mapValue

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    fun registerRecyclerViewScrollListener(recyclerView: RecyclerView, minHeight: Float, maxHeight: Float) {
        var scrollAmount = 0f
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val toolbar = (requireParentFragment() as? OnToolbarVisibilityChangedListener)
                if (dx == 0 && dy == 0) {
                    val firstVisibleItemPosition =
                        (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                    if (firstVisibleItemPosition == 0) {
                        toolbar?.onToolbarVisibilityChanged(0f)
                        scrollAmount = 0f
                    }
                } else {
                    scrollAmount += dy
                    toolbar?.onToolbarVisibilityChanged(
                        mapValue(scrollAmount.coerceIn(minHeight, maxHeight), minHeight, maxHeight, 0f, 1f)
                    )
                }
            }
        })
    }

}