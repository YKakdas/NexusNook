package moadgara.domain.games

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.data.games.repository.GamesRepository

class GetReleaseDateFilteredGamesUseCase(
  private val repository: GamesRepository,
  coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Pair<String, String>, ListOfGamesResponse>(coroutineDispatcher) {
    override fun execute(parameters: Pair<String, String>): Flow<NetworkResult<ListOfGamesResponse>> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page"] = "1"
        queryParams["ordering"] = "-released"
        queryParams["page_size"] = "40"
        queryParams["dates"] = parameters.first.plus(",").plus(parameters.second)
        return repository.fetchGames(queryParams)
    }
}