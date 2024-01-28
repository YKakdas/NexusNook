package moadgara.main.games_detail.viewholders

import moadgara.base.util.CoilUtil
import moadgara.main.R
import moadgara.main.databinding.LayoutGameDetailScreenshotsBinding
import moadgara.main.games_detail.GameDetailsNavigator
import moadgara.main.games_detail.listitems.GameDetailScreenshotData
import moadgara.uicomponent.adapter.GenericListItem
import moadgara.uicomponent.adapter.ItemClickListener
import moadgara.uicomponent.adapter.genericAdapter
import org.koin.java.KoinJavaComponent.inject

class GameDetailsScreenshotsViewHolder(val binding: LayoutGameDetailScreenshotsBinding) : GenericViewHolder(binding),
    ItemClickListener<String> {

    private val navigator: GameDetailsNavigator by inject(GameDetailsNavigator::class.java)

    override fun bindData(data: GenericListItem) {
        super.bindData(data as GameDetailScreenshotData)
        setupViewPager(data)
    }

    private fun setupViewPager(data: GameDetailScreenshotData) {
        binding.screenshotPager.run {
            val innerAdapter = genericAdapter<String> {
                itemLayoutResource(R.layout.layout_game_detail_screenshot_item_view)
                itemClickListener(this@GameDetailsScreenshotsViewHolder)
            }
            offscreenPageLimit = 3
            setPageTransformer(CubicPageTransformer())
            adapter = innerAdapter
            isClickable = false
            CoilUtil.prefetchImages(context, data.screenshots) {
                innerAdapter.submitList(data.screenshots)
                isClickable = true
            }
        }
    }

    override fun onItemClicked(listItem: String) {
        navigator.navigateToSingleImageViewer(listItem)
    }

}
