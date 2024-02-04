package moadgara.domain.games

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.data.games.repository.GamesRepository

internal class GetReleaseDateFilteredGamesUseCase(
    private val repository: GamesRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<List<String>, ListOfGamesResponse>(coroutineDispatcher) {
    override suspend fun execute(param: List<String>): NetworkResult<ListOfGamesResponse> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page_size"] = "40"
        queryParams["ordering"] = "released"
        queryParams["dates"] = param[0].plus(",").plus(param[1])
        return repository.fetchGames(queryParams)
    }
}