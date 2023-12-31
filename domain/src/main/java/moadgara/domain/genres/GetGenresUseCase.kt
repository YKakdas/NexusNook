package moadgara.domain.genres

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.data.games.repository.GamesRepository
import moadgara.data.genres.entity.ListOfGenresResponse
import moadgara.data.genres.repository.GenresRepository

class GetGenresUseCase(
  private val repository: GenresRepository,
  coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, ListOfGenresResponse>(coroutineDispatcher) {
    override fun execute(parameters: Unit): Flow<NetworkResult<ListOfGenresResponse>> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page"] = "1"
        queryParams["page_size"] = "40"
        return repository.fetchGenres(queryParams)
    }
}