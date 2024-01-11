package moadgara.domain.games

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.games.entity.GameDetailsFromIdResponse
import moadgara.data.games.repository.GamesRepository

class GetGameDetailsFromIdUseCase(
    private val repository: GamesRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<Int, GameDetailsFromIdResponse>(coroutineDispatcher) {
    override suspend fun execute(param: Int): NetworkResult<GameDetailsFromIdResponse> {
        return repository.fetchGameDetailsFromID(param, emptyMap())
    }
}