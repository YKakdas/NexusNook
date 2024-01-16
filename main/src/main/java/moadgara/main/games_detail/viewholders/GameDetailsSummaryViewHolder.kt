package moadgara.main.games_detail.viewholders

import androidx.recyclerview.widget.GridLayoutManager
import moadgara.base.extension.orZero
import moadgara.main.R
import moadgara.main.databinding.LayoutGameDetailSummaryListItemBinding
import moadgara.main.games_detail.listitems.GameDetailsSummaryListItem
import moadgara.main.games_detail.listitems.SpannableText
import moadgara.main.games_detail.listitems.SummaryListItemType
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
            // TODO: Find a more elegant way to do that
            layoutManager = GridLayoutManager(context, 5).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val item = innerItems[position]
                        return when (item.type) {
                            SummaryListItemType.WEBSITE -> if (position % 2 == 0) 5 else 3
                            else -> return if (item.secondSpan?.length.orZero() > 15) 3 else 2
                        }
                    }

                }
            }
            adapter = innerAdapter
            setHasFixedSize(true)
            innerAdapter.submitList(innerItems)
        }
    }

}