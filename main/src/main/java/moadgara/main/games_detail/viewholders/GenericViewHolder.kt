package moadgara.main.games_detail.viewholders

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import moadgara.main.BR
import moadgara.uicomponent.adapter.GenericListItem


open class GenericViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root), ViewHolder {
    override fun bindData(data: GenericListItem) {
        binding.setVariable(BR.data, data)
        binding.executePendingBindings()
    }
}
