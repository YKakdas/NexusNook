package moadgara.main.paging

import moadgara.main.paging.PagingItemData

data class CommonPagingItemData(
    override val id: Int,
    val image: String,
    val name: String,
    val gamesCount: String,
    val topGames: List<Pair<String, Int>>,
    val action: (() -> Unit)? = null
) :PagingItemData(id)
