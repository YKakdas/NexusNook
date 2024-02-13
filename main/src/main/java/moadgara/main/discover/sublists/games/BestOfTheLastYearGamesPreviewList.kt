package moadgara.main.discover.sublists.games

import androidx.lifecycle.MutableLiveData
import com.moadgara.common_model.network.NetworkResult
import moadgara.base.util.tryCast
import moadgara.data.ResponseMapper
import moadgara.data.games.entity.GameDetailResponse
import moadgara.domain.ListType
import moadgara.domain.games.GetGamesUseCase
import moadgara.main.R
import moadgara.main.discover.PreviewListMetaData
import moadgara.main.discover.PreviewListViewData
import moadgara.main.discover.sublists.PreviewList
import moadgara.main.discover.sublists.PreviewListCommonParameters
import java.util.Calendar

class BestOfTheLastYearGamesPreviewList(
    private val previewListCommonParameters: PreviewListCommonParameters, private val useCase: GetGamesUseCase
) : PreviewList() {

    private val viewLiveData = MutableLiveData<PreviewListViewData>()

    override val previewListType: ListType
        get() = ListType.BEST_OF_LAST_YEAR

    override fun getPreviewListMetaData(): PreviewListMetaData {
        val year = Calendar.getInstance().get(Calendar.YEAR).minus(1)
        val listTitle =
            previewListCommonParameters.resourceProvider.getString(R.string.discover_best_of_the_last_year_games_title, year)
        return PreviewListMetaData(
            title = listTitle,
            buttonTitle = previewListCommonParameters.resourceProvider.getString(R.string.see_all_button_title),
            buttonAction = { previewListCommonParameters.discoverNavigator.navigateToAllGamesPage(listTitle, previewListType) }
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
        return useCase.invoke(ListType.BEST_OF_LAST_YEAR)
    }

}