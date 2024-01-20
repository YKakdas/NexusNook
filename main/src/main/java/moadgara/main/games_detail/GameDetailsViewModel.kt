package moadgara.main.games_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.launch
import moadgara.data.games.entity.GameDetailsFromIdResponse
import moadgara.domain.games.GetGameDetailsFromIdUseCase
import moadgara.main.games_detail.listitems.GameDetailsHeaderListItem
import moadgara.main.games_detail.listitems.GameDetailsHorizontalDivider
import moadgara.main.games_detail.listitems.GameDetailsSummaryListItem
import moadgara.main.games_detail.listitems.SpannableText
import moadgara.main.games_detail.listitems.SummaryListItemType
import moadgara.uicomponent.adapter.GenericListItem

class GameDetailsViewModel(val getGameDetailsFromIdUseCase: GetGameDetailsFromIdUseCase) : ViewModel() {

    private val gameDetailsData = MutableLiveData<List<GenericListItem>>()
    private val message = MutableLiveData<String?>()

    fun getGameDetailsData() = gameDetailsData

    fun getMessage() = message
    fun fetchData(gameId: Int) {
        viewModelScope.launch {
            when (val result = getGameDetailsFromIdUseCase(gameId)) {
                is NetworkResult.Success -> result.data?.let { prepareData(it) }
                is NetworkResult.Failure -> message.value = result.message
            }
        }
    }

    private fun prepareData(data: GameDetailsFromIdResponse) {
        val list = mutableListOf<GenericListItem>()

        val header = GameDetailsHeaderListItem(imageUrl = data.backgroundImageAdditionalUri, name = data.name ?: data.slug)
        list.add(header)
        list.add(GameDetailsHorizontalDivider())
        
        val playtime: String? = data.playTime.takeIf { it != null && it > 0 }?.toString()

        val summary = GameDetailsSummaryListItem(
            listOf(
                SpannableText("Release Date", data.releasedDate, SummaryListItemType.RELEASE_DATE),
                SpannableText("Publisher", data.publishers?.firstOrNull()?.publisherName, SummaryListItemType.PUBLISHER),
                SpannableText("Play Time", playtime?.plus(" hours"), SummaryListItemType.PLAY_TIME),
                SpannableText("Website", data.websiteUri, SummaryListItemType.WEBSITE)
            )
        )
        list.add(summary)

        list.add(GameDetailsHorizontalDivider())

        gameDetailsData.value = list
    }
}