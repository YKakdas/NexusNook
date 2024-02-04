package moadgara.main.games_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import moadgara.base.extension.addIfNotNull
import moadgara.base.extension.orZero
import moadgara.base.extension.stringRes
import moadgara.base.util.ResourceProvider
import moadgara.base.util.tryCast
import moadgara.data.games.entity.GameDetailResponse
import moadgara.data.games.entity.GameDetailsFromIdResponse
import moadgara.data.games.entity.ScreenshotsResponse
import moadgara.domain.games.GetGameDetailsFromIdUseCase
import moadgara.domain.games.GetScreenshotsFromGameIdUseCase
import moadgara.main.R
import moadgara.main.games_detail.listitems.GameDetailGenresData
import moadgara.main.games_detail.listitems.GameDetailScreenshotData
import moadgara.main.games_detail.listitems.GameDetailsDescriptionData
import moadgara.main.games_detail.listitems.GameDetailsHeaderData
import moadgara.main.games_detail.listitems.GameDetailsHorizontalDivider
import moadgara.main.games_detail.listitems.GameDetailsMetascoreRatingData
import moadgara.main.games_detail.listitems.GameDetailsSummaryData
import moadgara.main.games_detail.listitems.SpannableText
import moadgara.uicomponent.adapter.GenericListItem

class GameDetailsViewModel(
    val resourceProvider: ResourceProvider,
    val getGameDetailsFromIdUseCase: GetGameDetailsFromIdUseCase,
    val getScreenshotsFromGameIdUseCase: GetScreenshotsFromGameIdUseCase
) :
    ViewModel() {

    private val gameDetailsData = MutableLiveData<List<GenericListItem>>()
    private val message = MutableLiveData<String?>()
    private val progress = MutableLiveData<Boolean>()
    private var responseFromPreviousFragment: GameDetailResponse? = null

    private var gameDetailsFromId: GameDetailsFromIdResponse? = null
    private var screenshotsFromId: ScreenshotsResponse? = null

    private var dataIsPrepared = false

    fun getGameDetailsData() = gameDetailsData

    fun getMessage() = message

    fun getProgress() = progress

    fun fetchData(gameId: Int, response: GameDetailResponse?) {
        if (dataIsPrepared) return

        progress.value = true
        this.responseFromPreviousFragment = response

        viewModelScope.launch {
            val gameDetails = async { getGameDetailsFromIdUseCase(gameId) }
            val screenshots = async { getScreenshotsFromGameIdUseCase(gameId) }

            val deferred = awaitAll(gameDetails, screenshots)

            processGameDetails(deferred[0])
            processScreenshots(deferred[1])

            prepareData()
        }
    }

    private fun processGameDetails(networkResult: NetworkResult<Any>) {
        when (networkResult) {
            is NetworkResult.Failure -> message.value = networkResult.message
            is NetworkResult.Success -> gameDetailsFromId = networkResult.data.tryCast()
        }
    }

    private fun processScreenshots(networkResult: NetworkResult<Any>) {
        if (networkResult is NetworkResult.Success) {
            screenshotsFromId = networkResult.data.tryCast()
        }
    }


    private fun prepareData() {
        val list = mutableListOf<GenericListItem>()

        gameDetailsFromId?.let {
            list.addAll(prepareHeader(it))
            list.addAll(prepareMetascoreRatingView(it))
            list.addAll(prepareSummary(it))
            list.addAll(prepareGenres(it))
            list.addAll(prepareDescription(it))

            screenshotsFromId?.let { screenshots ->
                list.addAll(prepareScreenshots(screenshots))
            }
        }
        gameDetailsData.value = list
        dataIsPrepared = true
    }

    private fun prepareHeader(data: GameDetailsFromIdResponse): List<GenericListItem> {
        val image = data.backgroundImageAdditionalUri ?: data.backgroundImageUri
        val header = GameDetailsHeaderData(
            defaultImage = image.orEmpty(),
            imageUrlList = responseFromPreviousFragment?.shortScreenshots?.mapNotNull { it.screenshotImage },
            name = data.name ?: data.slug
        )
        return listOf(header)
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

        return if (spannableTexts.isNotEmpty()) {
            listOf(GameDetailsSummaryData(spannableTexts), GameDetailsHorizontalDivider())
        } else {
            emptyList()
        }
    }

    private fun prepareGenres(data: GameDetailsFromIdResponse): List<GenericListItem> {
        val genres = data.genres
        if (genres.isNullOrEmpty()) {
            return emptyList()
        }

        val genreNames = data.genres?.mapNotNull { it.genreName }.orEmpty().toMutableList()
        return listOf(GameDetailGenresData(genreNames), GameDetailsHorizontalDivider())
    }

    private fun prepareMetascoreRatingView(data: GameDetailsFromIdResponse): List<GenericListItem> {
        val ratingCount = data.ratingsCount.orZero
        val ratingScore = if (ratingCount > 0) data.rating else null

        val gameDetailsMetascoreRatingData = GameDetailsMetascoreRatingData(data.metaCritic, ratingScore, "($ratingCount votes)")

        return if (ratingCount <= 0 && (data.metaCritic == null || data.metaCritic == 0))
            emptyList()
        else
            listOf(gameDetailsMetascoreRatingData, GameDetailsHorizontalDivider())
    }

    private fun prepareDescription(data: GameDetailsFromIdResponse): List<GenericListItem> {
        val descriptionText = data.description ?: data.descriptionRaw
        return if (!descriptionText.isNullOrEmpty()) {
            listOf(GameDetailsDescriptionData(descriptionText), GameDetailsHorizontalDivider())
        } else {
            emptyList()
        }
    }

    private fun prepareScreenshots(data: ScreenshotsResponse): List<GenericListItem> {
        return if (data.screenshots.isNullOrEmpty()) {
            emptyList()
        } else {
            listOf(
                GameDetailScreenshotData(data.screenshots!!.mapNotNull { it.screenshotUrl }),
                GameDetailsHorizontalDivider()
            )
        }

    }
}