package moadgara.main.games_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.launch
import moadgara.data.games.entity.GameDetailsFromIdResponse
import moadgara.domain.games.GetGameDetailsFromIdUseCase
import moadgara.main.games_detail.listitems.GameDetailsHeaderListItem
import moadgara.uicomponent.adapter.GenericListItem

class GameDetailsViewModel(val getGameDetailsFromIdUseCase: GetGameDetailsFromIdUseCase) : ViewModel() {

    private val gameDetailsData = MutableLiveData<List<GenericListItem>>()
    private val message = MutableLiveData<String?>()

    fun getGameDetailsData() = gameDetailsData

    fun getMessage() = message
    fun fetchData(gameId: Int) {
        viewModelScope.launch {
            when (val result = getGameDetailsFromIdUseCase(gameId)) {
                is NetworkResult.Success -> result.data?.let { prepareData(it) }
                is NetworkResult.Failure -> message.value = result.message
            }
        }
    }

    private fun prepareData(data: GameDetailsFromIdResponse) {
        val list = mutableListOf<GenericListItem>()
        val header = GameDetailsHeaderListItem(imageUrl = data.backgroundImageAdditionalUri)

        list.add(header)

        gameDetailsData.value = list
    }
}