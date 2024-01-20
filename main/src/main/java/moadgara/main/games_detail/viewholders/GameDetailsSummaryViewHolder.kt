package moadgara.main.games_detail.viewholders

import androidx.recyclerview.widget.GridLayoutManager
import moadgara.main.R
import moadgara.main.databinding.LayoutGameDetailSummaryListItemBinding
import moadgara.main.games_detail.listitems.GameDetailsSummaryData
import moadgara.main.games_detail.listitems.SpannableText
import moadgara.uicomponent.adapter.GenericListItem
import moadgara.uicomponent.adapter.genericAdapter

class GameDetailsSummaryViewHolder(val binding: LayoutGameDetailSummaryListItemBinding) : GenericViewHolder(binding) {

    override fun bindData(data: GenericListItem) {
        super.bindData(data as GameDetailsSummaryData)
        setupRecyclerView(data)
    }

    private fun setupRecyclerView(data: GameDetailsSummaryData) {
        binding.recyclerView.run {
            val innerAdapter = genericAdapter<SpannableText> { itemLayoutResource(R.layout.layout_spannable_text) }
            layoutManager = GridLayoutManager(context, 2)
            adapter = innerAdapter
            setHasFixedSize(false)
            innerAdapter.submitList(data.spannableTexts)
        }
    }

}