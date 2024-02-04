package moadgara.main.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import moadgara.base.extension.orZero
import moadgara.domain.games.GetPagingGamesUseCase
import moadgara.main.discover.DiscoverNavigator
import moadgara.main.paging.games.PagedGameItemData

// TODO It is not generic yet, refactor to accept any usecase type
class GenericPagingViewModel(val useCase: GetPagingGamesUseCase, val navigator: DiscoverNavigator) : ViewModel() {
    private val data = MutableLiveData<PagingData<GenericPagingItemData>>()

    fun getData(): LiveData<PagingData<GenericPagingItemData>> {
        return data.cachedIn(viewModelScope)
    }

    fun fetchData() {
        viewModelScope.launch {
            useCase.invoke(Unit).cachedIn(viewModelScope).collectLatest {
                data.value =
                    it.map { response ->
                        PagedGameItemData(
                            response.id.orZero,
                            response.backgroundImageUri.orEmpty(),
                            response.name.orEmpty(),
                            response.metaCritic
                        ) {
                            navigator.navigateToGameDetailPage(response.id, response.name, null)
                        }
                    }
            }
        }
    }
}