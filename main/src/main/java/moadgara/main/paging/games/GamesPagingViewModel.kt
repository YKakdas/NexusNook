package moadgara.main.paging.games

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
import moadgara.domain.games.GetGamesPagingUseCase
import moadgara.main.discover.DiscoverNavigator
import moadgara.main.paging.BasePagingViewModel
import moadgara.main.paging.PagingItemData

class GamesPagingViewModel(private val useCase: GetGamesPagingUseCase, private val navigator: DiscoverNavigator) :
    BasePagingViewModel() {
    private val data = MutableLiveData<PagingData<GamesPagingItemData>>()

    override fun fetchData() {
        viewModelScope.launch {
            useCase.invoke(Unit).cachedIn(viewModelScope).collectLatest {
                data.value =
                    it.map { response ->
                        with(response) {
                            GamesPagingItemData(
                                id.orZero,
                                backgroundImageUri.orEmpty(),
                                platforms?.mapNotNull { platform -> platform.platform?.platformId }.orEmpty(),
                                name.orEmpty(),
                                metaCritic
                            ) {
                                navigator.navigateToGameDetailPage(id, name, null)
                            }
                        }

                    }
            }
        }
    }

    override fun getObserver(): LiveData<PagingData<PagingItemData>> {
        return data.cachedIn(viewModelScope).tryCastNotNull()
    }
}