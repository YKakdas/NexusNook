package moadgara.domain.games

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.data.games.repository.GamesRepository

class GetBestOfTheYearUseCase(
    private val repository: GamesRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<Unit, ListOfGamesResponse>(coroutineDispatcher) {
    override suspend fun execute(param: Unit): NetworkResult<ListOfGamesResponse> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page"] = "1"
        queryParams["discover"] = "true"
        queryParams["ordering"] = "relevance"
        queryParams["page_size"] = "40"
        return repository.fetchBestOfTheYear(queryParams)
    }
}