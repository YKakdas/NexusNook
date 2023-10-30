package moadgara.domain.games

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.data.games.repository.GamesRepository

class GetTrendingGamesUseCase(
    private val repository: GamesRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, ListOfGamesResponse>(coroutineDispatcher) {
    override fun execute(parameters: Unit): Flow<NetworkResult<ListOfGamesResponse>> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["pageNum"] = "0"
        queryParams["discover"] = "true"
        queryParams["ordering"] = "-relevance"
        queryParams["page_size"] = "40"
        return repository.fetchTrendingGames(queryParams)
    }
}