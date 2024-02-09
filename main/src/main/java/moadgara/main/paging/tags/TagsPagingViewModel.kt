package moadgara.main.paging.tags

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
import moadgara.domain.tags.GetTagsPagingUseCase
import moadgara.main.R
import moadgara.main.discover.DiscoverNavigator
import moadgara.main.paging.BasePagingViewModel
import moadgara.main.paging.CommonPagingItemData
import moadgara.main.paging.PagingItemData

class TagsPagingViewModel(
    private val useCase: GetTagsPagingUseCase,
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
                            tagId.orZero,
                            tagImageBackground.orEmpty(),
                            tagName.orEmpty(),
                            resourceProvider.getString(R.string.game_count, tagGamesCount.orZero.toString()),
                            tagGames?.map { game -> game.gameName.orEmpty() to game.gameAddedCount.orZero }.orEmpty()
                        ) {
                            navigator.navigateToTagDetail(
                                resourceProvider.getString(R.string.detail_page_title, tagName), tagId
                            )
                        }
                    }
                }
            }
        }
    }
}