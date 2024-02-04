package moadgara.main.paging.games

import moadgara.main.R
import moadgara.main.paging.GenericPagingItemData
import moadgara.uicomponent.adapter.GenericListItem

data class PagedGameItemData(
    override val id: Int,
    val image: String,
    val name: String,
    val metascore: Int?,
    val action: (() -> Unit)? = null
) : GenericListItem(R.layout.layout_paged_game_item_view), GenericPagingItemData
