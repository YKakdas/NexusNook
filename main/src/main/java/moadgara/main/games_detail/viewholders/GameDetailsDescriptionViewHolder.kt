package moadgara.main.games_detail.viewholders

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.extension.smoothSnapToPosition
import moadgara.main.databinding.LayoutGameDetailDescriptionBinding
import moadgara.main.games_detail.listitems.GameDetailsDescriptionData
import moadgara.uicomponent.adapter.GenericListItem

class GameDetailsDescriptionViewHolder(
    val binding: LayoutGameDetailDescriptionBinding,
    val recyclerView: RecyclerView
) : GenericViewHolder(binding) {

    @SuppressLint("ClickableViewAccessibility")
    override fun bindData(data: GenericListItem) {
        super.bindData(data as GameDetailsDescriptionData)
        binding.expandableDescription.setOnAnimationChangeListener { isBeingAnimated, scrollAmount, isExpanded ->
            recyclerView.setOnTouchListener { _, _ -> isBeingAnimated }
            if (isBeingAnimated) {
                recyclerView.scrollBy(0, scrollAmount)
            } else {
                recyclerView.smoothSnapToPosition(absoluteAdapterPosition - 2, LinearSmoothScroller.SNAP_TO_START)
            }
        }
    }

}