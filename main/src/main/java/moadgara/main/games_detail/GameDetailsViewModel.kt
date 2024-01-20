package moadgara.main.games_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.launch
import moadgara.base.extension.addIfNotNull
import moadgara.base.extension.stringRes
import moadgara.base.util.ResourceProvider
import moadgara.data.games.entity.GameDetailsFromIdResponse
import moadgara.domain.games.GetGameDetailsFromIdUseCase
import moadgara.main.R
import moadgara.main.games_detail.listitems.GameDetailsHeaderData
import moadgara.main.games_detail.listitems.GameDetailsHorizontalDivider
import moadgara.main.games_detail.listitems.GameDetailsSummaryData
import moadgara.main.games_detail.listitems.SpannableText
import moadgara.uicomponent.adapter.GenericListItem

class GameDetailsViewModel(val resourceProvider: ResourceProvider, val getGameDetailsFromIdUseCase: GetGameDetailsFromIdUseCase) :
    ViewModel() {

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

        list.addAll(prepareHeader(data))
        list.addAll(prepareSummary(data))

        gameDetailsData.value = list
    }

    private fun prepareHeader(data: GameDetailsFromIdResponse): List<GenericListItem> {
        val header = GameDetailsHeaderData(imageUrl = data.backgroundImageAdditionalUri, name = data.name ?: data.slug)
        return listOf(header, GameDetailsHorizontalDivider())
    }

    private fun prepareSummary(data: GameDetailsFromIdResponse): List<GenericListItem> {
        val spannableTexts = mutableListOf<SpannableText>()
        data.run {
            val releaseDate = data.releasedDate.takeIf { !it.isNullOrEmpty() }
            val publisher = data.publishers?.firstOrNull()?.publisherName.takeIf { !it.isNullOrEmpty() }
            val playtime = data.playTime.takeIf { it != null && it > 0 }?.toString()
            val website = data.websiteUri.takeIf { !it.isNullOrEmpty() }

            spannableTexts.addIfNotNull(
                SpannableText(R.string.game_details_summary_release_date_title.stringRes(resourceProvider), releaseDate),
                releaseDate
            )

            spannableTexts.addIfNotNull(
                SpannableText(R.string.game_details_summary_publisher_title.stringRes(resourceProvider), publisher),
                publisher
            )

            spannableTexts.addIfNotNull(
                SpannableText(R.string.game_details_summary_playtime_title.stringRes(resourceProvider), playtime?.plus(" hours")),
                playtime
            )

            spannableTexts.addIfNotNull(
                SpannableText(R.string.game_details_summary_website_title.stringRes(resourceProvider), website),
                website
            )
        }

        return listOf(GameDetailsSummaryData(spannableTexts), GameDetailsHorizontalDivider())
    }
}