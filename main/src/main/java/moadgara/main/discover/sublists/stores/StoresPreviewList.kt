package moadgara.main.discover.sublists.stores

import androidx.lifecycle.MutableLiveData
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import moadgara.domain.stores.GetStoresUseCase
import moadgara.main.R
import moadgara.main.discover.PreviewListMetaData
import moadgara.main.discover.PreviewListViewData
import moadgara.main.discover.sublists.PreviewList
import moadgara.main.discover.sublists.PreviewListCommonParameters
import moadgara.main.discover.sublists.PreviewListType

class StoresPreviewList(
    private val previewListCommonParameters: PreviewListCommonParameters, private val useCase: GetStoresUseCase
) :
    PreviewList(previewListCommonParameters, useCase) {

    private val viewLiveData = MutableLiveData<PreviewListViewData>()

    override val previewListType: PreviewListType
        get() = PreviewListType.STORES

    override fun getPreviewListMetaData(): PreviewListMetaData {
        val listTitle = previewListCommonParameters.resourceProvider.getString(R.string.discover_stores_title)
        return PreviewListMetaData(
            title = listTitle,
            buttonTitle = previewListCommonParameters.resourceProvider.getString(R.string.see_all_button_title),
            buttonAction = { previewListCommonParameters.discoverNavigator.navigateToAllStores(listTitle) }
        )
    }

    override fun getInnerItemAction(name: String?): () -> Unit {
        return { previewListCommonParameters.discoverNavigator.navigateToStoreDetail(name) }
    }

    override fun getViewLiveData(): MutableLiveData<PreviewListViewData> {
        return viewLiveData
    }

    override suspend fun invokeUseCase(): Flow<NetworkResult<Any>> {
        return useCase.invoke(Unit)
    }
}