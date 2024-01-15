package moadgara.main.games_detail.viewholders

import androidx.recyclerview.widget.GridLayoutManager
import moadgara.main.R
import moadgara.main.databinding.LayoutGameDetailSummaryListItemBinding
import moadgara.main.games_detail.listitems.GameDetailsSummaryListItem
import moadgara.main.games_detail.listitems.SpannableText
import moadgara.uicomponent.adapter.GenericListItem
import moadgara.uicomponent.adapter.genericAdapter

class GameDetailsSummaryViewHolder(val binding: LayoutGameDetailSummaryListItemBinding) : GenericViewHolder(binding) {

    override fun bindData(data: GenericListItem) {
        super.bindData(data as GameDetailsSummaryListItem)
        setupRecyclerView(data)
    }

    private fun setupRecyclerView(data: GameDetailsSummaryListItem) {
        binding.recyclerView.run {
            val innerItems = data.spannableTexts.filter { !it.secondSpan.isNullOrEmpty() }
            val innerAdapter = genericAdapter<SpannableText> { itemLayoutResource(R.layout.layout_spannable_text) }
            layoutManager = GridLayoutManager(context, 2)
            adapter = innerAdapter
            setHasFixedSize(true)
            innerAdapter.submitList(innerItems)
        }
    }
}