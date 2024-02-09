package moadgara.main.paging.creators

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import moadgara.base.extension.orZero
import moadgara.base.util.ResourceProvider
import moadgara.base.util.tryCastNotNull
import moadgara.domain.creators.GetCreatorsPagingUseCase
import moadgara.main.R
import moadgara.main.discover.DiscoverNavigator
import moadgara.main.paging.BasePagingViewModel
import moadgara.main.paging.CommonPagingItemData
import moadgara.main.paging.PagingItemData

class CreatorsPagingViewModel(
    private val useCase: GetCreatorsPagingUseCase,
    private val navigator: DiscoverNavigator,
    private val resourceProvider: ResourceProvider
) : BasePagingViewModel() {
    private val data = MutableLiveData<PagingData<CommonPagingItemData>>()

    override fun getObserver(): LiveData<PagingData<PagingItemData>> {
        return data.cachedIn(viewModelScope).tryCastNotNull<LiveData<PagingData<PagingItemData>>>()
    }

    override fun fetchData() {
        viewModelScope.launch {
            useCase.invoke(Unit).cachedIn(viewModelScope).collectLatest {
                data.value = it.map { response ->
                    with(response) {
                        CommonPagingItemData(
                            creatorId.orZero,
                            creatorImageBackground.orEmpty(),
                            creatorName.orEmpty(),
                            resourceProvider.getString(R.string.game_count, creatorGamesCount.orZero.toString()),
                            creatorGames?.map { game -> game.gameName.orEmpty() to game.gameAddedCount.orZero }.orEmpty()
                        ) {
                            navigator.navigateToCreatorDetail(
                                resourceProvider.getString(R.string.detail_page_title, creatorName), creatorId
                            )
                        }
                    }
                }
            }
        }
    }
}