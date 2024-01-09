package moadgara.uicomponent

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView


class PreloadLinearLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(context, orientation, reverseLayout) {

    private var extraSpace: Int = 1200
    private var preloadItemCount: Int = 6
    private var orientationHelper: OrientationHelper = OrientationHelper.createOrientationHelper(this, getOrientation())

    fun setPreloadItemCount(preloadItemCount: Int) {
        this.preloadItemCount = preloadItemCount
    }

    fun setExtraSpace(extraSpace: Int) {
        this.extraSpace = extraSpace
    }

    override fun collectAdjacentPrefetchPositions(
        dx: Int, dy: Int, state: RecyclerView.State,
        layoutPrefetchRegistry: LayoutPrefetchRegistry
    ) {
        super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry)

        val delta = if (orientation == HORIZONTAL) dx else dy // the scroll distance based on the orientation of the RecyclerView
        if (childCount == 0 || delta == 0) return // If there are no child views or the scroll delta is zero, the method exits early as there's nothing to prefetch.
        val layoutDirection =
            if (delta > 0) 1 else -1 // the direction of layout scrolling based on the delta.
        val child: View = getClosestChild(layoutDirection) ?: return
        val currentPosition = getPosition(child) + layoutDirection
        val scrollingOffset: Int
        if (layoutDirection == 1) {
            scrollingOffset = (orientationHelper.getDecoratedEnd(child) - orientationHelper.endAfterPadding)
            for (i in currentPosition + 1 until currentPosition + preloadItemCount + 1) {
                if (i >= 0 && i < state.itemCount) {
                    layoutPrefetchRegistry.addPosition(i, 0.coerceAtLeast(scrollingOffset))
                }
            }
        }
    }

    override fun calculateExtraLayoutSpace(state: RecyclerView.State, extraLayoutSpace: IntArray) {
        super.calculateExtraLayoutSpace(state, extraLayoutSpace)
        extraLayoutSpace[1] += 1200
    }

    private fun getClosestChild(layoutDirection: Int): View? {
        return getChildAt(if (layoutDirection == -1) 0 else childCount - 1)
    }
}