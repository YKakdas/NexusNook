package moadgara.domain.games

import com.moadgara.common_model.DateUtil
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.data.games.repository.GamesRepository
import java.util.Calendar

class GetGamesUseCase(
    private val repository: GamesRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<GameListType, ListOfGamesResponse>(coroutineDispatcher) {
    override suspend fun execute(param: GameListType): NetworkResult<ListOfGamesResponse> {
        return when (param) {
            GameListType.BEST_OF_THE_YEAR -> {
                val year = Calendar.getInstance().get(Calendar.YEAR)
                GetBestOfTheYearUseCase(repository).invoke(year)
            }

            GameListType.BEST_OF_THE_LAST_YEAR -> {
                val year = Calendar.getInstance().get(Calendar.YEAR).minus(1)
                GetBestOfTheYearUseCase(repository).invoke(year)
            }

            GameListType.TRENDING -> {
                GetTrendingGamesUseCase(repository).invoke(Unit)
            }

            GameListType.RECENTLY_ADDED_POPULAR -> {
                GetRecentlyAddedPopularGamesUseCase(repository).invoke(Unit)
            }

            GameListType.RELEASED_THIS_MONTH -> {
                val dateRange = DateUtil.getDateRangeForMonth()
                GetReleaseDateFilteredGamesUseCase(repository).invoke(dateRange)
            }

            GameListType.RELEASED_THIS_WEEK -> {
                val dateRange = DateUtil.getDateRangeForWeek(DateUtil.getCurrentWeek())
                GetReleaseDateFilteredGamesUseCase(repository).invoke(dateRange)
            }

            GameListType.RELEASING_NEXT_WEEK -> {
                val dateRange = DateUtil.getDateRangeForWeek(DateUtil.getCurrentWeek().plus(1))
                GetReleaseDateFilteredGamesUseCase(repository).invoke(dateRange)
            }
        }

    }
}