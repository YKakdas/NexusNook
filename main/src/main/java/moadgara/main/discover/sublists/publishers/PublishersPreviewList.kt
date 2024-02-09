package moadgara.main.discover.sublists.publishers

import androidx.lifecycle.MutableLiveData
import com.moadgara.common_model.network.NetworkResult
import moadgara.data.ResponseMapper
import moadgara.domain.ListType
import moadgara.domain.publishers.GetPublishersUseCase
import moadgara.main.R
import moadgara.main.discover.PreviewListMetaData
import moadgara.main.discover.PreviewListViewData
import moadgara.main.discover.sublists.PreviewList
import moadgara.main.discover.sublists.PreviewListCommonParameters

class PublishersPreviewList(
    private val previewListCommonParameters: PreviewListCommonParameters, private val useCase: GetPublishersUseCase
) : PreviewList(previewListCommonParameters, useCase) {

    private val viewLiveData = MutableLiveData<PreviewListViewData>()

    override val previewListType: ListType
        get() = ListType.PUBLISHERS

    override fun getPreviewListMetaData(): PreviewListMetaData {
        val listTitle = previewListCommonParameters.resourceProvider.getString(R.string.discover_publishers_title)
        return PreviewListMetaData(title = listTitle,
            buttonTitle = previewListCommonParameters.resourceProvider.getString(R.string.see_all_button_title),
            buttonAction = { previewListCommonParameters.discoverNavigator.navigateToAllPublishers(listTitle) })
    }

    override fun getInnerItemAction(id: Int?, name: String?, response: Any?): () -> Unit {
        return {
            previewListCommonParameters.discoverNavigator.navigateToPublisherDetail(
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