package moadgara.main.games_detail.listitems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import moadgara.main.R
import moadgara.main.games_detail.viewholders.GameDetailsSummaryViewHolder
import moadgara.main.games_detail.viewholders.GameDetailsViewType
import moadgara.main.games_detail.viewholders.GenericViewHolder
import moadgara.uicomponent.adapter.GenericListItem
import moadgara.uicomponent.adapter.genericAdapter

class GameDetailsAdapter : ListAdapter<GenericListItem, GenericViewHolder>(ItemDiffCallback) {

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
        return when (viewType) {
            GameDetailsViewType.SUMMARY.type -> {
                GameDetailsSummaryViewHolder(DataBindingUtil.inflate(layoutInflater, viewType, parent, false))
            }

            else -> GenericViewHolder(DataBindingUtil.inflate(layoutInflater, viewType, parent, false))
        }
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layout
    }

}
