package moadgara.main.discover.sublists

import androidx.lifecycle.MutableLiveData
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import moadgara.domain.creators.GetCreatorsUseCase
import moadgara.main.R
import moadgara.main.discover.PreviewListMetaData
import moadgara.main.discover.PreviewListViewData

class CreatorsPreviewList(
    private val previewListCommonParameters: PreviewListCommonParameters, private val useCase: GetCreatorsUseCase
) :
    PreviewList(previewListCommonParameters, useCase) {

    private val viewLiveData = MutableLiveData<PreviewListViewData>()

    override val previewListType: PreviewListType
        get() = PreviewListType.CREATORS

    override fun getPreviewListMetaData(): PreviewListMetaData {
        val listTitle = previewListCommonParameters.resourceProvider.getString(R.string.discover_creators_title)
        return PreviewListMetaData(
            title = listTitle,
            buttonTitle = previewListCommonParameters.resourceProvider.getString(R.string.see_all_button_title),
            buttonAction = { previewListCommonParameters.discoverNavigator.navigateToAllCreators(listTitle) }
        )
    }

    override fun getInnerItemAction(name: String?): () -> Unit {
        return { previewListCommonParameters.discoverNavigator.navigateToCreatorDetail(name) }
    }

    override fun getViewLiveData(): MutableLiveData<PreviewListViewData> {
        return viewLiveData
    }

    override suspend fun invokeUseCase(): Flow<NetworkResult<Any>> {
        return useCase.invoke(Unit)
    }
}