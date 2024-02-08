package moadgara.domain.games

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moadgara.common_model.usecase.PagingFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.games.entity.GameDetailResponse
import moadgara.data.games.repository.GamesRepository
import moadgara.domain.ListType

class GetGamesPagingUseCase(
    private val gamesRepository: GamesRepository,
    private val gamesUseCase: GetGamesUseCase,
    private val listType: ListType,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PagingFlowUseCase<Unit, GameDetailResponse>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<PagingData<GameDetailResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 40),
            pagingSourceFactory = { GamesPagingSource(gamesRepository, gamesUseCase, listType) }
        ).flow
    }

}