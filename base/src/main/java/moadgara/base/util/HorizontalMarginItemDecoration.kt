package moadgara.base.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalMarginItemDecoration(private val margin: Float) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                left = margin.toInt() / 2
                right = margin.toInt()
            } else if (parent.getChildAdapterPosition(view) == parent.childCount - 1) {
                right = margin.toInt() / 2
            } else {
                right = margin.toInt()
            }
        }
    }
}