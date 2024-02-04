package moadgara.domain.games

import com.moadgara.common_model.DateUtil
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.data.games.repository.GamesRepository

internal class GetBestOfTheYearUseCase(
    private val repository: GamesRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<Int, ListOfGamesResponse>(coroutineDispatcher) {
    override suspend fun execute(param: Int): NetworkResult<ListOfGamesResponse> {
        val dates = DateUtil.getDateRangeForYear(param)
        val queryParams = mutableMapOf<String, String>()
        queryParams["ordering"] = "-added"
        queryParams["page_size"] = "40"
        queryParams["dates"] = dates[0].plus(",").plus(dates[1])
        return repository.fetchGames(queryParams)
    }
}