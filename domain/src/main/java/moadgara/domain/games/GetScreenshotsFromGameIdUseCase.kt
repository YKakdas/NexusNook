package moadgara.domain.games

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.games.entity.ScreenshotsResponse
import moadgara.data.games.repository.GamesRepository

class GetScreenshotsFromGameIdUseCase(
    private val repository: GamesRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<Int, ScreenshotsResponse>(coroutineDispatcher) {
    override suspend fun execute(param: Int): NetworkResult<ScreenshotsResponse> {
        return repository.fetchScreenshotsFromID(param, emptyMap())
    }
}