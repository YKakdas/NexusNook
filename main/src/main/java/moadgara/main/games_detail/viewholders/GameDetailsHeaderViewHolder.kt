package moadgara.main.games_detail.viewholders

import coil.load
import moadgara.base.extension.prefetchThenCycleImagesRepeatedly
import moadgara.base.util.CoilUtil
import moadgara.main.databinding.LayoutGameDetailHeaderListItemBinding
import moadgara.main.games_detail.listitems.GameDetailsHeaderData
import moadgara.uicomponent.adapter.GenericListItem

class GameDetailsHeaderViewHolder(val binding: LayoutGameDetailHeaderListItemBinding) : GenericViewHolder(binding) {

    override fun bindData(data: GenericListItem) {
        super.bindData(data as GameDetailsHeaderData)
        fetchAndCycleImages(data)
    }

    private fun fetchAndCycleImages(data: GameDetailsHeaderData) {
        binding.image.run {
            load(data.defaultImage, CoilUtil.getCachedCoilImageLoader(this.context))
            prefetchThenCycleImagesRepeatedly(data.imageUrlList, 5000L)
        }
    }
}
