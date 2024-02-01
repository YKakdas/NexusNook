package moadgara.main.discover.sublists.games

import androidx.lifecycle.MutableLiveData
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.DateUtil
import moadgara.base.util.tryCast
import moadgara.data.ResponseMapper
import moadgara.data.games.entity.GameDetailResponse
import moadgara.domain.games.GetReleaseDateFilteredGamesUseCase
import moadgara.main.R
import moadgara.main.discover.PreviewListMetaData
import moadgara.main.discover.PreviewListViewData
import moadgara.main.discover.sublists.PreviewList
import moadgara.main.discover.sublists.PreviewListCommonParameters
import moadgara.main.discover.sublists.PreviewListType

class ThisWeekReleasedGamesPreviewList(
    private val previewListCommonParameters: PreviewListCommonParameters, private val useCase: GetReleaseDateFilteredGamesUseCase
) : PreviewList(previewListCommonParameters, useCase) {

    private val viewLiveData = MutableLiveData<PreviewListViewData>()

    override val previewListType: PreviewListType
        get() = PreviewListType.THIS_WEEK_RELEASED

    override fun getPreviewListMetaData(): PreviewListMetaData {
        val listTitle = previewListCommonParameters.resourceProvider.getString(R.string.discover_released_this_week_games_title)
        return PreviewListMetaData(
            title = listTitle,
            buttonTitle = previewListCommonParameters.resourceProvider.getString(R.string.see_all_button_title),
            buttonAction = { previewListCommonParameters.discoverNavigator.navigateToAllGamesPage(listTitle) }
        )
    }

    override fun getInnerItemAction(id: Int?, name: String?, response: Any?): () -> Unit {
        val responseData = response.tryCast<GameDetailResponse>()
        return { previewListCommonParameters.discoverNavigator.navigateToGameDetailPage(id, name, responseData) }
    }

    override fun getViewLiveData(): MutableLiveData<PreviewListViewData> {
        return viewLiveData
    }

    override suspend fun invokeUseCase(): NetworkResult<ResponseMapper> {
        return useCase.invoke(DateUtil.getDateRangeForWeek(DateUtil.getCurrentWeek()))
    }

}