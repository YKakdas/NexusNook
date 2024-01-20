package moadgara.main.games_detail.listitems

import moadgara.main.R
import moadgara.uicomponent.adapter.GenericListItem

data class GameDetailsSummaryData(
    val spannableTexts: List<SpannableText>
) : GenericListItem(R.layout.layout_game_detail_summary_list_item)
