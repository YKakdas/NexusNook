package moadgara.main.discover.sublists

import androidx.lifecycle.MutableLiveData
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.domain.games.GetRecentlyAddedPopularGamesUseCase
import moadgara.main.R
import moadgara.main.discover.PreviewListMetaData
import moadgara.main.discover.PreviewListViewData

class RecentlyAddedPopularGamesPreviewList(
  private val previewListCommonParameters: PreviewListCommonParameters, private val useCase: GetRecentlyAddedPopularGamesUseCase
) : PreviewList(previewListCommonParameters, useCase) {

    private val viewLiveData = MutableLiveData<PreviewListViewData>()

    override val previewListType: PreviewListType
        get() = PreviewListType.RECENTLY_ADDED_POPULAR

    override fun getPreviewListMetaData(): PreviewListMetaData {
        val listTitle = previewListCommonParameters.resourceProvider.getString(R.string.discover_recently_added_popular_games_title)
        return PreviewListMetaData(
          title = listTitle,
          buttonTitle = previewListCommonParameters.resourceProvider.getString(R.string.see_all_button_title),
          buttonAction = { previewListCommonParameters.discoverNavigator.navigateToAllGamesPage(listTitle) }
        )
    }

    override fun getInnerItemAction(name: String?): () -> Unit {
        return { previewListCommonParameters.discoverNavigator.navigateToGameDetailPage(name)}
    }

    override fun getViewLiveData(): MutableLiveData<PreviewListViewData> {
        return viewLiveData
    }

    override suspend fun invokeUseCase(): Flow<NetworkResult<Any>> {
        return useCase.invoke(Unit)
    }

}