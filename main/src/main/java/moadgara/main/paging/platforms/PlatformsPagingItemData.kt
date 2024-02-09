package moadgara.main.paging.platforms

import moadgara.main.R
import moadgara.main.paging.PagingItemData
import moadgara.uicomponent.adapter.GenericListItem

data class PlatformsPagingItemData(
    override val id: Int,
    val image: String,
    val name: String,
    val gamesCount: String,
    val topGames: List<Pair<String, Int>>,
    val action: (() -> Unit)? = null
) :PagingItemData(id)
