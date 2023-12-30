package moadgara.main.discover

import moadgara.main.R
import moadgara.uicomponent.adapter.GenericListItem

data class PreviewListItemData(
  val imageUrl: String?,
  val gameTitle: String?,
  val action: (() -> Unit)?
) :
  GenericListItem(R.layout.layout_small_card_view_list_item)
