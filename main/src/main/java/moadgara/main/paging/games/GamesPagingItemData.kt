package moadgara.main.paging.games

import moadgara.main.paging.PagingItemData

data class GamesPagingItemData(
    override val id: Int,
    val image: String,
    val platforms: List<Int?>,
    val name: String,
    val metascore: Int?,
    val action: (() -> Unit)? = null
) : PagingItemData(id)
