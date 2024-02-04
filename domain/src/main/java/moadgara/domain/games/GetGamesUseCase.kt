package moadgara.domain.games

import com.moadgara.common_model.DateUtil
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
        return when (param) {
            ListType.BEST_OF_THE_YEAR -> {
                val year = Calendar.getInstance().get(Calendar.YEAR)
                GetBestOfTheYearUseCase(repository).invoke(year)
            }

            ListType.BEST_OF_LAST_YEAR -> {
                val year = Calendar.getInstance().get(Calendar.YEAR).minus(1)
                GetBestOfTheYearUseCase(repository).invoke(year)
            }

            ListType.TRENDING -> {
                GetTrendingGamesUseCase(repository).invoke(Unit)
            }

            ListType.RECENTLY_ADDED_POPULAR -> {
                GetRecentlyAddedPopularGamesUseCase(repository).invoke(Unit)
            }

            ListType.THIS_MONTH_RELEASED -> {
                val dateRange = DateUtil.getDateRangeForMonth()
                GetReleaseDateFilteredGamesUseCase(repository).invoke(dateRange)
            }

            ListType.THIS_WEEK_RELEASED -> {
                val dateRange = DateUtil.getDateRangeForWeek(DateUtil.getCurrentWeek())
                GetReleaseDateFilteredGamesUseCase(repository).invoke(dateRange)
            }

            ListType.RELEASING_NEXT_WEEK -> {
                val dateRange = DateUtil.getDateRangeForWeek(DateUtil.getCurrentWeek().plus(1))
                GetReleaseDateFilteredGamesUseCase(repository).invoke(dateRange)
            }

            else -> throw IllegalStateException("Invalid list item type has been provided!")
        }

    }
}