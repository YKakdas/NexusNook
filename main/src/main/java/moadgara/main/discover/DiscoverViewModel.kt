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
import moadgara.main.R

class DiscoverViewModel(
    private val resourceProvider: ResourceProvider,
    private val discoverNavigator: DiscoverNavigator,
    private val useCases: List<Pair<FlowUseCase<Any, ListOfGamesResponse>, Any>>
) : ViewModel() {

    private val message = MutableLiveData<String?>()
    private val previewListsLiveData = mutableListOf<MutableLiveData<List<PreviewListItemData>>>()

    init {
        repeat(useCases.size) {
            previewListsLiveData.add(MutableLiveData<List<PreviewListItemData>>())
        }
    }

    fun getMessage(): LiveData<String?> = message

    fun preparePageMetaData(): List<PreviewListMetaData> {
        val previewLists = mutableListOf<PreviewListMetaData>()
        with(previewLists) {
            add(getPreviewListMetaData(resourceProvider.getString(R.string.discover_trending_games_title)))
            add(getPreviewListMetaData(resourceProvider.getString(R.string.discover_best_of_the_year_games_title)))
            add(getPreviewListMetaData(resourceProvider.getString(R.string.discover_recently_added_popular_games_title)))
            add(getPreviewListMetaData(resourceProvider.getString(R.string.discover_released_this_month_games_title)))
            add(getPreviewListMetaData(resourceProvider.getString(R.string.discover_released_this_week_games_title)))
            add(getPreviewListMetaData(resourceProvider.getString(R.string.discover_releasing_next_week_games_title)))
        }

        return previewLists
    }

    fun getAllPreviewListLiveData(): List<LiveData<List<PreviewListItemData>>> =
        previewListsLiveData

    fun fetchData() {
        viewModelScope.launch {
            useCases.forEachIndexed { index, useCasePair ->
                launch {
                    fetchData(
                        previewListsLiveData[index],
                        useCasePair.first,
                        useCasePair.second
                    )
                }
            }
        }
    }

    private suspend fun fetchData(
        listLiveData: MutableLiveData<List<PreviewListItemData>>,
        useCase: FlowUseCase<Any, ListOfGamesResponse>,
        parameters: Any
    ) {
        useCase(parameters).transformWhile {
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
                            it.shortScreenshots?.firstOrNull()?.screenshotImage,
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