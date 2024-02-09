package moadgara.main.paging.platforms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import moadgara.base.extension.orZero
import moadgara.base.util.tryCastNotNull
import moadgara.domain.platforms.GetPlatformsPagingUseCase
import moadgara.main.discover.DiscoverNavigator
import moadgara.main.paging.BasePagingViewModel
import moadgara.main.paging.PagingItemData

class PlatformsPagingViewModel(private val useCase: GetPlatformsPagingUseCase, private val navigator: DiscoverNavigator) :
    BasePagingViewModel() {
    private val data = MutableLiveData<PagingData<PlatformsPagingItemData>>()

    override fun getObserver(): LiveData<PagingData<PagingItemData>> {
        return data.cachedIn(viewModelScope).tryCastNotNull<LiveData<PagingData<PagingItemData>>>()
    }

    override fun fetchData() {
        viewModelScope.launch {
            useCase.invoke(Unit).cachedIn(viewModelScope).collectLatest {
                data.value = it.map { response ->
                    with(response) {
                        PlatformsPagingItemData(
                            platformId.orZero,
                            platformImageBackground.orEmpty(),
                            platformName.orEmpty(),
                            "# ${platformGamesCount.orZero}",
                            platformGames?.map { game -> game.gameName.orEmpty() to game.gameAddedCount.orZero }
                                .orEmpty()
                        ) { navigator.navigateToPlatformDetail(platformId) }
                    }
                }
            }
        }
    }
}