package moadgara.main.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.flow.transformWhile
import kotlinx.coroutines.launch
import moadgara.domain.games.GetTrendingGamesUseCase

class DiscoverViewModel(private val getTrendingGamesUseCase: GetTrendingGamesUseCase) :
    ViewModel() {

    private val progress = MutableLiveData<Boolean>()
    private val message = MutableLiveData<String?>()

    fun getMessage(): LiveData<String?> = message

    fun getProgress(): LiveData<Boolean> = progress

    init {
        val job = viewModelScope.launch {
            getTrendingGamesUseCase(Unit).transformWhile {
                emit(it)
                it is NetworkResult.Loading
            }.collect {
                when (it) {
                    is NetworkResult.Loading -> progress.value = true
                    is NetworkResult.Failure -> {
                        progress.value = false
                        message.value = it.message
                    }

                    is NetworkResult.Success -> {
                        progress.value = false
                        message.value = "yeeee222ey"
                    }
                }
            }

        }
    }

}