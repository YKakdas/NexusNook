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

class DiscoverViewModel(
    private val enablePrefetch: Boolean = false,
    private val prefetchAmount: Int = 6,
    private val previewLists: List<PreviewList>
) : ViewModel() {

    private val message = MutableLiveData<String?>()
    private val images = MutableLiveData<List<String>>()

    private var errorCount = 0

    private lateinit var data: List<Pair<PreviewList, NetworkResult<ResponseMapper>>>

    fun getMessage(): LiveData<String?> = message

    fun getImages(): LiveData<List<String>> = images

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
        if (!::data.isInitialized) {
            viewModelScope.launch {
                data = previewLists.map { previewList ->
                    async {
                        val result = previewList.invokeUseCase()
                        previewList to result // Pair the network result with its corresponding meta data
                    }
                }.awaitAll()
                images.value = processData()
            }
        } else {
            processData()
        }

    }

    private fun processData(): List<String> {
        val imagesToPrefetch = mutableListOf<String>()
        data.forEach {
            val previewList = it.first
            val result = it.second
            if (result is NetworkResult.Failure) {
                errorCount++
                previewList.getViewLiveData().value = null
                if (errorCount == previewLists.size) {
                    message.value = result.message
                }
            } else if (result is NetworkResult.Success) {
                if (enablePrefetch && imagesToPrefetch.isEmpty()) {
                    imagesToPrefetch.addAll(getPortionOfList(list = result.data?.toImageList()))
                } else {
                    images.value = emptyList()
                }
                toPreviewListViewData(result.data, previewList)
            }
        }
        return imagesToPrefetch
    }

    private fun getPortionOfList(list: List<String>?): List<String> {
        return if (list != null && list.size < prefetchAmount) {
            list
        } else {
            list?.subList(0, prefetchAmount) ?: emptyList()
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