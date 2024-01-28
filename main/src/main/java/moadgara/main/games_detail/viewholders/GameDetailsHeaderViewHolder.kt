package moadgara.main.games_detail.viewholders

import moadgara.main.BR
import moadgara.main.databinding.LayoutGameDetailHeaderListItemBinding
import moadgara.main.games_detail.listitems.GameDetailsHeaderData
import moadgara.uicomponent.adapter.GenericListItem

class GameDetailsHeaderViewHolder(val binding: LayoutGameDetailHeaderListItemBinding) : GenericViewHolder(binding) {

    override fun bindData(data: GenericListItem) {
        super.bindData(data as GameDetailsHeaderData)
        fetchAndCycleImages(data)
    }

    private fun fetchAndCycleImages(data: GameDetailsHeaderData) {
        binding.setVariable(BR.imageUrl, data.defaultImage)
        binding.executePendingBindings()
        //    prefetchThenCycleImagesRepeatedly(data.imageUrlList, 5000L)
    }
}
