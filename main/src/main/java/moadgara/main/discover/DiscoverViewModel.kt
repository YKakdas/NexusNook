package moadgara.main.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.flow.transformWhile
import kotlinx.coroutines.launch
import moadgara.base.ResourceProvider
import moadgara.domain.games.GetTrendingGamesUseCase
import moadgara.main.R

class DiscoverViewModel(
    private val resourceProvider: ResourceProvider,
    private val getTrendingGamesUseCase: GetTrendingGamesUseCase
) :
    ViewModel() {

    private val message = MutableLiveData<String?>()
    private val trendingGamesPreviewList = MutableLiveData<List<PreviewListItemData>>()

    fun getMessage(): LiveData<String?> = message

    fun getTrendingGamesPreviewList(): LiveData<List<PreviewListItemData>> =
        trendingGamesPreviewList

    fun preparePageMetaData(): List<PreviewListMetaData> {
        val previewLists = mutableListOf<PreviewListMetaData>()
        previewLists.add(
            PreviewListMetaData(
                resourceProvider.getString(R.string.discover_trending_games_title),
                resourceProvider.getString(R.string.see_all_button_title),
            ) {
                TODO("Add navigation on See All button action")
            })
        return previewLists
    }

    init {
        viewModelScope.launch {
            getTrendingGamesUseCase(Unit).transformWhile {
                emit(it)
                it is NetworkResult.Loading
            }.collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Failure -> {
                        message.value = networkResult.message
                    }

                    is NetworkResult.Success -> {
                        trendingGamesPreviewList.value = networkResult.data?.results?.map {
                            PreviewListItemData(
                                it.shortScreenshots?.get(0)?.screenshotImage,
                                it.name
                            )
                        }
                    }
                }
            }

        }
    }

}