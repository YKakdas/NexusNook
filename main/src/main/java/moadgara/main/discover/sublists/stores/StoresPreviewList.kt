package moadgara.main.discover.sublists.stores

import androidx.lifecycle.MutableLiveData
import com.moadgara.common_model.network.NetworkResult
import moadgara.data.ResponseMapper
import moadgara.domain.ListType
import moadgara.domain.stores.GetStoresUseCase
import moadgara.main.R
import moadgara.main.discover.PreviewListMetaData
import moadgara.main.discover.PreviewListViewData
import moadgara.main.discover.sublists.PreviewList
import moadgara.main.discover.sublists.PreviewListCommonParameters

class StoresPreviewList(
    private val previewListCommonParameters: PreviewListCommonParameters, private val useCase: GetStoresUseCase
) : PreviewList() {

    private val viewLiveData = MutableLiveData<PreviewListViewData>()

    override val previewListType: ListType
        get() = ListType.STORES

    override fun getPreviewListMetaData(): PreviewListMetaData {
        val listTitle = previewListCommonParameters.resourceProvider.getString(R.string.discover_stores_title)
        return PreviewListMetaData(title = listTitle,
            buttonTitle = previewListCommonParameters.resourceProvider.getString(R.string.see_all_button_title),
            buttonAction = { previewListCommonParameters.discoverNavigator.navigateToAllStores(listTitle) })
    }

    override fun getInnerItemAction(id: Int?, name: String?, response: Any?): () -> Unit {
        return {
            previewListCommonParameters.discoverNavigator.navigateToStoreDetail(
                previewListCommonParameters.resourceProvider.getString(R.string.detail_page_title, name), id
            )
        }
    }

    override fun getViewLiveData(): MutableLiveData<PreviewListViewData> {
        return viewLiveData
    }

    override suspend fun invokeUseCase(): NetworkResult<ResponseMapper> {
        return useCase.invoke(Unit)
    }
}