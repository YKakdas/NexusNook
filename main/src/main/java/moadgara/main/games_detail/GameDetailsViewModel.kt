package moadgara.main.games_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.launch
import moadgara.data.games.entity.GameDetailsFromIdResponse
import moadgara.domain.games.GetGameDetailsFromIdUseCase

class GameDetailsViewModel(val getGameDetailsFromIdUseCase: GetGameDetailsFromIdUseCase) : ViewModel() {

    private val gameDetailsData = MutableLiveData<GameDetailsFromIdResponse?>()
    private val message = MutableLiveData<String?>()

    fun getGameDetailsData() = gameDetailsData

    fun getMessage() = message
    fun fetchData(gameId: Int) {
        viewModelScope.launch {
            when (val result = getGameDetailsFromIdUseCase(gameId)) {
                is NetworkResult.Success -> gameDetailsData.value = result.data
                is NetworkResult.Failure -> message.value = result.message
            }
        }
    }
}