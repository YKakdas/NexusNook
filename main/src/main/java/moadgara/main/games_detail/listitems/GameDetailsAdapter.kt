package moadgara.main.games_detail.listitems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import moadgara.base.util.tryCastNotNull
import moadgara.main.databinding.LayoutGameDetailDescriptionBinding
import moadgara.main.databinding.LayoutGameDetailHeaderListItemBinding
import moadgara.main.databinding.LayoutGameDetailScreenshotsBinding
import moadgara.main.databinding.LayoutGameDetailSummaryListItemBinding
import moadgara.main.games_detail.viewholders.GameDetailsDescriptionViewHolder
import moadgara.main.games_detail.viewholders.GameDetailsHeaderViewHolder
import moadgara.main.games_detail.viewholders.GameDetailsScreenshotsViewHolder
import moadgara.main.games_detail.viewholders.GameDetailsSummaryViewHolder
import moadgara.main.games_detail.viewholders.GameDetailsViewType
import moadgara.main.games_detail.viewholders.GenericViewHolder
import moadgara.uicomponent.adapter.GenericListItem

class GameDetailsAdapter : ListAdapter<GenericListItem, GenericViewHolder>(ItemDiffCallback) {
    private lateinit var recyclerView: RecyclerView

    object ItemDiffCallback : DiffUtil.ItemCallback<GenericListItem>() {
        override fun areItemsTheSame(oldItem: GenericListItem, newItem: GenericListItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GenericListItem, newItem: GenericListItem): Boolean {
            return oldItem.layout == newItem.layout
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return when (viewType) {
            GameDetailsViewType.HEADER.type -> GameDetailsHeaderViewHolder(
                viewDataBinding.tryCastNotNull<LayoutGameDetailHeaderListItemBinding>()
            )

            GameDetailsViewType.SUMMARY.type -> GameDetailsSummaryViewHolder(
                viewDataBinding.tryCastNotNull<LayoutGameDetailSummaryListItemBinding>()
            )

            GameDetailsViewType.DESCRIPTION.type -> GameDetailsDescriptionViewHolder(
                viewDataBinding.tryCastNotNull<LayoutGameDetailDescriptionBinding>(), recyclerView
            )

            GameDetailsViewType.SCREENSHOTS.type -> GameDetailsScreenshotsViewHolder(
                viewDataBinding.tryCastNotNull<LayoutGameDetailScreenshotsBinding>()
            )

            else -> GenericViewHolder(DataBindingUtil.inflate(layoutInflater, viewType, parent, false))
        }
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layout
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }
}

