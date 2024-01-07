package moadgara.domain.genres

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.genres.entity.ListOfGenresResponse
import moadgara.data.genres.repository.GenresRepository

class GetGenresUseCase(
    private val repository: GenresRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<Unit, ListOfGenresResponse>(coroutineDispatcher) {
    override suspend fun execute(param: Unit): NetworkResult<ListOfGenresResponse> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page"] = "1"
        queryParams["page_size"] = "40"
        return repository.fetchGenres(queryParams)
    }
}