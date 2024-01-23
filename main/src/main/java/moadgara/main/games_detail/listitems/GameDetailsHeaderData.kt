package moadgara.main.games_detail.listitems

import moadgara.main.R
import moadgara.uicomponent.adapter.GenericListItem

data class GameDetailsHeaderData(
    val defaultImage: String,
    val imageUrlList: List<String>?,
    val name: String?
) : GenericListItem(R.layout.layout_game_detail_header_list_item)
