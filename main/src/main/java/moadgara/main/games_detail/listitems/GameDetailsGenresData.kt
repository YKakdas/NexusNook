package moadgara.main.games_detail.listitems

import moadgara.main.R
import moadgara.uicomponent.adapter.GenericListItem

data class GameDetailsGenresData(val genreNames: List<String>) : GenericListItem(R.layout.layout_game_detail_genres)