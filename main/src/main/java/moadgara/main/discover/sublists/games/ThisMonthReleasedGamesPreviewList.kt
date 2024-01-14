package moadgara.main.discover.sublists.games

import androidx.lifecycle.MutableLiveData
import com.moadgara.common_model.network.NetworkResult
import moadgara.base.util.DateUtil
import moadgara.data.ResponseMapper
import moadgara.domain.games.GetReleaseDateFilteredGamesUseCase
import moadgara.main.R
import moadgara.main.discover.PreviewListMetaData
import moadgara.main.discover.PreviewListViewData
import moadgara.main.discover.sublists.PreviewList
import moadgara.main.discover.sublists.PreviewListCommonParameters
import moadgara.main.discover.sublists.PreviewListType

class ThisMonthReleasedGamesPreviewList(
    private val previewListCommonParameters: PreviewListCommonParameters, private val useCase: GetReleaseDateFilteredGamesUseCase
) : PreviewList(previewListCommonParameters, useCase) {

    private val viewLiveData = MutableLiveData<PreviewListViewData>()

    override val previewListType: PreviewListType
        get() = PreviewListType.THIS_MONTH_RELEASED

    override fun getPreviewListMetaData(): PreviewListMetaData {
        val listTitle = previewListCommonParameters.resourceProvider.getString(R.string.discover_released_this_month_games_title)
        return PreviewListMetaData(
            title = listTitle,
            buttonTitle = previewListCommonParameters.resourceProvider.getString(R.string.see_all_button_title),
            buttonAction = { previewListCommonParameters.discoverNavigator.navigateToAllGamesPage(listTitle) }
        )
    }

    override fun getInnerItemAction(id: Int?, name: String?): () -> Unit {
        return { previewListCommonParameters.discoverNavigator.navigateToGameDetailPage(id, name) }
    }

    override fun getViewLiveData(): MutableLiveData<PreviewListViewData> {
        return viewLiveData
    }

    override suspend fun invokeUseCase(): NetworkResult<ResponseMapper> {
        return useCase.invoke(DateUtil.getDateRangeForMonth())
    }

}