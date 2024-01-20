package moadgara.main.games_detail.viewholders

import moadgara.main.R

enum class GameDetailsViewType(val type: Int) {
    HEADER(R.layout.layout_game_detail_header_list_item),
    SUMMARY(R.layout.layout_game_detail_summary_list_item),
    DIVIDER(R.layout.layout_horizontal_divider),
    METASCORE_RATING(R.layout.layout_game_detail_metascore_rating),
    DESCRIPTION(R.layout.layout_game_detail_description)
}