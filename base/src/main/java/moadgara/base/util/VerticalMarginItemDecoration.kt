package moadgara.base.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VerticalMarginItemDecoration(private val margin: Float, private val ignoreFirstTopMargin: Boolean = false) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val numColumns = (parent.layoutManager as? GridLayoutManager)?.spanCount ?: 1
        with(outRect) {
            if (!ignoreFirstTopMargin) {
                val childPosition = parent.getChildAdapterPosition(view)
                if (childPosition < numColumns) {
                    top = margin.toInt() / 2
                }
            }
            bottom = margin.toInt()
        }
    }
}