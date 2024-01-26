package moadgara.uicomponent

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.extension.orZero

class LinearLayoutManagerWithAccurateOffset(context: Context, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(context, orientation, reverseLayout) {

    // map of child adapter position to its height.
    private val childSizesMap = mutableMapOf<Int, Int>()

    override fun onLayoutCompleted(state: RecyclerView.State) {
        super.onLayoutCompleted(state)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child != null) {
                childSizesMap[getPosition(child)] = child.height
            }
        }
    }

    override fun computeVerticalScrollOffset(state: RecyclerView.State): Int {
        if (childCount == 0) {
            return 0
        }
        val firstChildPosition = findFirstVisibleItemPosition()
        val firstChild = findViewByPosition(firstChildPosition)
        var scrolledY: Int = -firstChild?.y.orZero.toInt()
        for (i in 0 until firstChildPosition) {
            scrolledY += childSizesMap[i] ?: 0
        }
        return scrolledY
    }

}