package moadgara.main.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.flow.transformWhile
import kotlinx.coroutines.launch
import moadgara.data.creators.entity.ListOfCreatorsResponse
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.data.genres.entity.ListOfGenresResponse
import moadgara.data.platforms.entity.ListOfPlatformsResponse
import moadgara.data.publishers.entity.ListOfPublishersResponse
import moadgara.data.stores.entity.ListOfStoresResponse
import moadgara.main.discover.sublists.PreviewList
import timber.log.Timber

class DiscoverViewModel(private val previewLists: List<PreviewList>) : ViewModel() {

    private val message = MutableLiveData<String?>()
    private var errorCount = 0

    fun getMessage(): LiveData<String?> = message

    fun preparePageMetaData(): List<PreviewListMetaData> {
        val previewListsMetaData = mutableListOf<PreviewListMetaData>()
        previewLists.forEach { previewListsMetaData.add(it.getPreviewListMetaData()) }
        return previewListsMetaData
    }

    fun getAllPreviewListsLiveData(): List<LiveData<PreviewListViewData>> {
        val observables = mutableListOf<LiveData<PreviewListViewData>>()
        previewLists.forEach { observables.add(it.getViewLiveData()) }
        return observables
    }

    fun fetchData() {
        viewModelScope.launch {
            previewLists.forEach { previewList ->
                launch {
                    fetchData(previewList)
                }
            }
        }
    }

    private suspend fun fetchData(previewList: PreviewList) {
        previewList.invokeUseCase().transformWhile {
            emit(it)
            it is NetworkResult.Loading
        }.collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Failure -> {
                    errorCount++
                    Timber.d(errorCount.toString())
                    if (errorCount == previewLists.size) {
                        message.value = networkResult.message
                    }
                    previewList.getViewLiveData().value = null
                }

                is NetworkResult.Success -> {
                    when (networkResult.data) {
                        is ListOfGamesResponse? -> {
                            showListOfGamesSublist(networkResult, previewList)
                        }

                        is ListOfGenresResponse? -> {
                            showListOfGenresSublist(networkResult, previewList)
                        }

                        is ListOfPlatformsResponse? -> {
                            showListOfPlatformsSublist(networkResult, previewList)
                        }

                        is ListOfPublishersResponse? -> {
                            showListOfPublishersSublist(networkResult, previewList)
                        }

                        is ListOfStoresResponse? -> {
                            showListOfStoresSublist(networkResult, previewList)
                        }

                        is ListOfCreatorsResponse? -> {
                            showListOfCreatorsSublist(networkResult, previewList)
                        }
                    }

                }
            }
        }

    }

    private fun showListOfGamesSublist(
        networkResult: NetworkResult.Success<Any>,
        previewList: PreviewList
    ) {
        val data = networkResult.data as ListOfGamesResponse?
        previewList.getViewLiveData().value = PreviewListViewData(data?.results?.map {
            PreviewListItemData(
                it.shortScreenshots?.firstOrNull()?.screenshotImage,
                it.name,
                previewList.getInnerItemAction(it.name)
            )
        })
    }

    private fun showListOfGenresSublist(
        networkResult: NetworkResult.Success<Any>,
        previewList: PreviewList
    ) {
        val data = networkResult.data as ListOfGenresResponse?
        previewList.getViewLiveData().value = PreviewListViewData(data?.results?.map {
            PreviewListItemData(
                it.genreImageBackground, it.genreName, previewList.getInnerItemAction(it.genreName)
            )
        })
    }

    private fun showListOfPlatformsSublist(
        networkResult: NetworkResult.Success<Any>,
        previewList: PreviewList
    ) {
        val data = networkResult.data as ListOfPlatformsResponse?
        previewList.getViewLiveData().value = PreviewListViewData(data?.results?.map {
            PreviewListItemData(
                it.platformImage ?: it.platformImageBackground, it.platformName, previewList.getInnerItemAction(it.platformName)
            )
        })
    }

    private fun showListOfPublishersSublist(
        networkResult: NetworkResult.Success<Any>,
        previewList: PreviewList
    ) {
        val data = networkResult.data as ListOfPublishersResponse?
        previewList.getViewLiveData().value = PreviewListViewData(data?.results?.map {
            PreviewListItemData(
                it.publisherImageBackground, it.publisherName, previewList.getInnerItemAction(it.publisherName)
            )
        })
    }

    private fun showListOfStoresSublist(
        networkResult: NetworkResult.Success<Any>,
        previewList: PreviewList
    ) {
        val data = networkResult.data as ListOfStoresResponse?
        previewList.getViewLiveData().value = PreviewListViewData(data?.results?.map {
            PreviewListItemData(
                it.storeImageBackground, it.storeName, previewList.getInnerItemAction(it.storeName)
            )
        })
    }

    private fun showListOfCreatorsSublist(
        networkResult: NetworkResult.Success<Any>,
        previewList: PreviewList
    ) {
        val data = networkResult.data as ListOfCreatorsResponse?
        previewList.getViewLiveData().value = PreviewListViewData(data?.results?.map {
            PreviewListItemData(
                it.creatorImage ?: it.creatorImageBackground, it.creatorName, previewList.getInnerItemAction(it.creatorName)
            )
        })
    }

}