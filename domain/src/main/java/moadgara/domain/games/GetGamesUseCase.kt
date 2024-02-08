package moadgara.domain.games

import com.moadgara.common_model.DateUtil
import com.moadgara.common_model.currentWeek
import com.moadgara.common_model.currentYear
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.data.games.repository.GamesRepository
import moadgara.domain.ListType
import java.util.Calendar

class GetGamesUseCase(
    private val repository: GamesRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<ListType, ListOfGamesResponse>(coroutineDispatcher) {
    override suspend fun execute(param: ListType): NetworkResult<ListOfGamesResponse> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page_size"] = "40"
        queryParams["ordering"] = "-added"

        return when (param) {
            ListType.TRENDING -> {
                queryParams["ordering"] = "relevance"
                repository.fetchTrendingGames(queryParams)
            }

            ListType.ALL_TIME_TOP_250 -> {
                queryParams["ordering"] = "relevance"
                repository.fetchAllTimeTop250(queryParams)
            }

            ListType.BEST_OF_THE_YEAR -> {
                val year = Calendar.getInstance().currentYear()
                val dates = DateUtil.getDateRangeForYear(year)
                queryParams["dates"] = dates[0].plus(",").plus(dates[1])
                repository.fetchGames(queryParams)
            }

            ListType.BEST_OF_LAST_YEAR -> {
                val year = Calendar.getInstance().currentYear().minus(1)
                val dates = DateUtil.getDateRangeForYear(year)
                queryParams["dates"] = dates[0].plus(",").plus(dates[1])
                repository.fetchGames(queryParams)
            }

            ListType.RECENTLY_ADDED_POPULAR -> {
                repository.fetchRecentlyAddedPopularGames(queryParams)
            }

            ListType.THIS_MONTH_RELEASED -> {
                val dateRange = DateUtil.getDateRangeForMonth()
                queryParams["ordering"] = "released"
                queryParams["dates"] = dateRange[0].plus(",").plus(dateRange[1])
                repository.fetchGames(queryParams)
            }

            ListType.THIS_WEEK_RELEASED -> {
                val dateRange = DateUtil.getDateRangeForWeek(Calendar.getInstance().currentWeek())
                queryParams["ordering"] = "released"
                queryParams["dates"] = dateRange[0].plus(",").plus(dateRange[1])
                repository.fetchGames(queryParams)
            }

            ListType.RELEASING_NEXT_WEEK -> {
                val dateRange = DateUtil.getDateRangeForWeek(Calendar.getInstance().currentWeek().plus(1))
                queryParams["ordering"] = "released"
                queryParams["dates"] = dateRange[0].plus(",").plus(dateRange[1])
                repository.fetchGames(queryParams)
            }

            else -> throw IllegalStateException("Invalid list item type has been provided!")
        }

    }
}