package moadgara.main.discover.sublists

import androidx.lifecycle.MutableLiveData
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.domain.games.GetTrendingGamesUseCase
import moadgara.main.R
import moadgara.main.discover.PreviewListMetaData
import moadgara.main.discover.PreviewListViewData

class TrendingGamesPreviewList(
  private val previewListCommonParameters: PreviewListCommonParameters, private val useCase: GetTrendingGamesUseCase
) : PreviewList(previewListCommonParameters, useCase) {

    private val viewLiveData = MutableLiveData<PreviewListViewData>()
    override val previewListType: PreviewListType
        get() = PreviewListType.TRENDING

    override fun getPreviewListMetaData(): PreviewListMetaData {
        val listTitle = previewListCommonParameters.resourceProvider.getString(R.string.discover_trending_games_title)
        return PreviewListMetaData(
          title = listTitle,
          buttonTitle = previewListCommonParameters.resourceProvider.getString(R.string.see_all_button_title),
          buttonAction = { previewListCommonParameters.discoverNavigator.navigateToAllGamesPage(listTitle) }
        )
    }

    class Container<out T>

    override fun getInnerItemAction(gameTitle: String?): () -> Unit {
        return { previewListCommonParameters.discoverNavigator.navigateToGameDetailPage(gameTitle)}
    }

    override fun getViewLiveData(): MutableLiveData<PreviewListViewData> {
        return viewLiveData
    }

    override suspend fun invokeUseCase(): Flow<NetworkResult<Any>> {
        return useCase.invoke(Unit)
    }

}