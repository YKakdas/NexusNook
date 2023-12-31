package moadgara.main.discover.sublists

import androidx.lifecycle.MutableLiveData
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import moadgara.domain.genres.GetGenresUseCase
import moadgara.domain.platforms.GetPlatformsUseCase
import moadgara.main.R
import moadgara.main.discover.PreviewListMetaData
import moadgara.main.discover.PreviewListViewData

class PlatformsPreviewList(
    private val previewListCommonParameters: PreviewListCommonParameters, private val useCase: GetPlatformsUseCase
) :
    PreviewList(previewListCommonParameters, useCase) {

    private val viewLiveData = MutableLiveData<PreviewListViewData>()

    override val previewListType: PreviewListType
        get() = PreviewListType.PLATFORMS

    override fun getPreviewListMetaData(): PreviewListMetaData {
        val listTitle = previewListCommonParameters.resourceProvider.getString(R.string.discover_platforms_title)
        return PreviewListMetaData(
            title = listTitle,
            buttonTitle = previewListCommonParameters.resourceProvider.getString(R.string.see_all_button_title),
            buttonAction = { previewListCommonParameters.discoverNavigator.navigateToAllPlatforms(listTitle) }
        )
    }

    override fun getInnerItemAction(name: String?): () -> Unit {
        return { previewListCommonParameters.discoverNavigator.navigateToPlatformDetail(name) }
    }

    override fun getViewLiveData(): MutableLiveData<PreviewListViewData> {
        return viewLiveData
    }

    override suspend fun invokeUseCase(): Flow<NetworkResult<Any>> {
        return useCase.invoke(Unit)
    }
}