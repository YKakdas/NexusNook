package moadgara.main.paging.games

import moadgara.main.R
import moadgara.main.paging.PagingItemData
import moadgara.uicomponent.adapter.GenericListItem

data class GamesPagingItemData(
    override val id: Int,
    val image: String,
    val name: String,
    val metascore: Int?,
    val action: (() -> Unit)? = null
): PagingItemData(id)
