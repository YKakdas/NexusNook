package moadgara.main.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.launch
import moadgara.data.ResponseMapper
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
        when (val networkResult = previewList.invokeUseCase()) {
            is NetworkResult.Failure -> {
                errorCount++
                Timber.d(errorCount.toString())
                if (errorCount == previewLists.size) {
                    message.value = networkResult.message
                }
                previewList.getViewLiveData().value = null
            }

            is NetworkResult.Success -> toPreviewListViewData(networkResult.data, previewList)
            else -> throw IllegalArgumentException()

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