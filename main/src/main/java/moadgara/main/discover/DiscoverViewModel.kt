package moadgara.main.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.FlowUseCase
import kotlinx.coroutines.flow.transformWhile
import kotlinx.coroutines.launch
import moadgara.base.ResourceProvider
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.domain.games.GetBestOfTheYearUseCase
import moadgara.domain.games.GetTrendingGamesUseCase
import moadgara.main.R

class DiscoverViewModel(
    private val resourceProvider: ResourceProvider,
    private val discoverNavigator: DiscoverNavigator,
    private val getTrendingGamesUseCase: GetTrendingGamesUseCase,
    private val getBestOfTheYearUseCase: GetBestOfTheYearUseCase
) :
    ViewModel() {

    private val message = MutableLiveData<String?>()
    private val trendingGamesPreviewList = MutableLiveData<List<PreviewListItemData>>()
    private val bestOfTheYearGamesPreviewList = MutableLiveData<List<PreviewListItemData>>()

    fun getMessage(): LiveData<String?> = message

    fun getTrendingGamesPreviewList(): LiveData<List<PreviewListItemData>> =
        trendingGamesPreviewList

    fun getBestOfTheYearGamesPreviewList(): LiveData<List<PreviewListItemData>> =
        bestOfTheYearGamesPreviewList

    fun preparePageMetaData(): List<PreviewListMetaData> {
        val previewLists = mutableListOf<PreviewListMetaData>()
        with(previewLists) {
            add(getPreviewListMetaData(resourceProvider.getString(R.string.discover_trending_games_title)))
            add(getPreviewListMetaData(resourceProvider.getString(R.string.discover_best_of_the_year_games_title)))
        }

        return previewLists
    }

    init {
        viewModelScope.launch {
            launch { getTrendingGames() }
            launch { getBestOfTheYearGames() }
        }
    }

    private suspend fun getTrendingGames() {
        fetchData(trendingGamesPreviewList, getTrendingGamesUseCase)
    }

    private suspend fun getBestOfTheYearGames() {
        fetchData(bestOfTheYearGamesPreviewList, getBestOfTheYearUseCase)
    }

    private suspend fun fetchData(
        listLiveData: MutableLiveData<List<PreviewListItemData>>,
        useCase: FlowUseCase<Unit, ListOfGamesResponse>
    ) {
        useCase(Unit).transformWhile {
            emit(it)
            it is NetworkResult.Loading
        }.collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Failure -> {
                    message.value = networkResult.message
                }

                is NetworkResult.Success -> {
                    listLiveData.value = networkResult.data?.results?.map {
                        PreviewListItemData(
                            it.shortScreenshots?.get(0)?.screenshotImage,
                            it.name
                        ) {
                            discoverNavigator.navigateToGameDetailPage(it.name ?: "")
                        }
                    }
                }
            }
        }
    }

    private fun getPreviewListMetaData(title: String) =
        PreviewListMetaData(
            title,
            resourceProvider.getString(R.string.see_all_button_title)
        ) { discoverNavigator.navigateToAllGamesPage(title) }

}