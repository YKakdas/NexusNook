package moadgara.main.paging.games

import moadgara.main.R
import moadgara.uicomponent.adapter.GenericListItem

data class GamesPagingItemData(
    val id: Int,
    val image: String,
    val name: String,
    val metascore: Int?,
    val action: (() -> Unit)? = null
) : GenericListItem(R.layout.layout_paging_game_item_view)
