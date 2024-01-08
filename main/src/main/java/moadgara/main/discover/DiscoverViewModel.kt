package moadgara.main.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import moadgara.data.ResponseMapper
import moadgara.main.discover.sublists.PreviewList

class DiscoverViewModel(private val previewLists: List<PreviewList>) : ViewModel() {

    private val message = MutableLiveData<String?>()
    private val progress = MutableLiveData<Boolean>()
    private var errorCount = 0

    fun getMessage(): LiveData<String?> = message

    fun getProgress(): LiveData<Boolean> = progress

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
            previewLists.map { previewList ->
                async {
                    val result = previewList.invokeUseCase()
                    previewList to result // Pair the network result with its corresponding meta data
                }
            }.awaitAll().forEach {
                val previewList = it.first
                val result = it.second
                if (result is NetworkResult.Failure) {
                    errorCount++
                    previewList.getViewLiveData().value = null
                    if (errorCount == previewLists.size) {
                        message.value = result.message
                    }
                } else if (result is NetworkResult.Success) {
                    toPreviewListViewData(result.data, previewList)
                }
            }

            progress.value = false
        }
    }

    private fun toPreviewListViewData(common: ResponseMapper?, previewList: PreviewList) {
        previewList.getViewLiveData().value = PreviewListViewData(common?.toSmallViewData()?.map {
            PreviewListItemData(
                it.imageUrl, it.name, previewList.getInnerItemAction(it.name)
            )
        })
    }

}