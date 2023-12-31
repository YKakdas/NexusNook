package moadgara.main.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.flow.transformWhile
import kotlinx.coroutines.launch
import moadgara.data.games.entity.ListOfGamesResponse
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
                    if (networkResult.data is ListOfGamesResponse?) {
                        val data = networkResult.data as ListOfGamesResponse?
                        previewList.getViewLiveData().value = PreviewListViewData(data?.results?.map {
                            PreviewListItemData(
                              it.shortScreenshots?.firstOrNull()?.screenshotImage, it.name, previewList.getInnerItemAction(it.name)
                            )
                        })
                    }

                }
            }
        }

    }


}