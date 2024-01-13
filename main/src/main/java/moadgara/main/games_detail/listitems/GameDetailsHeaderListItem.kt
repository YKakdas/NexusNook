package moadgara.main.games_detail.listitems

import androidx.annotation.DrawableRes
import moadgara.main.R
import moadgara.uicomponent.adapter.GenericListItem

data class GameDetailsHeaderListItem(
    @DrawableRes val defaultImage: Int = R.drawable.nexus_nook_image,
    val imageUrl: String?
) : GenericListItem(R.layout.layout_game_detail_header_list_item)
