package moadgara.main.games_detail.listitems

import moadgara.main.R
import moadgara.uicomponent.adapter.GenericListItem

data class GameDetailsMetascoreRatingData(
    val metascore: Int?,
    val rating: Float?,
    val ratingCount: String?,
) : GenericListItem(R.layout.layout_game_detail_metascore_rating)
