package moadgara.uicomponent

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class CustomLinearSnapHelper : LinearSnapHelper() {
    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        val firstVisiblePosition =
          (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
        val firstItem = 0
        val lastItem = layoutManager.itemCount - 1
        return when {
            firstItem == firstVisiblePosition -> layoutManager.findViewByPosition(firstVisiblePosition)
            lastItem == lastVisiblePosition -> layoutManager.findViewByPosition(lastVisiblePosition)
            else -> super.findSnapView(layoutManager)
        }
    }
}