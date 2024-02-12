package moadgara.main.games_detail.listitems

import moadgara.main.R
import moadgara.uicomponent.adapter.GenericListItem

data class GameDetailsChipData(
    val title: String, val chipList: List<Pair<Int?, String?>>, val action: ((Int?, String?) -> Unit)? = null
) : GenericListItem(R.layout.layout_game_detail_chip_layout)