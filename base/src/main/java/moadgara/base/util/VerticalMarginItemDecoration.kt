package moadgara.base.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalMarginItemDecoration(private val margin: Float) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = margin.toInt() / 2
            }
            bottom = margin.toInt()
        }
    }
}