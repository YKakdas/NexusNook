package moadgara.main.paging.games

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

class GamesPagingViewModel(private val useCase: GetPagingGamesUseCase, private val navigator: DiscoverNavigator) : ViewModel() {
    private val data = MutableLiveData<PagingData<GamesPagingItemData>>()

    fun getData(): LiveData<PagingData<GamesPagingItemData>> {
        return data.cachedIn(viewModelScope)
    }

    fun fetchData() {
        viewModelScope.launch {
            useCase.invoke(Unit).cachedIn(viewModelScope).collectLatest {
                data.value =
                    it.map { response ->
                        GamesPagingItemData(
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