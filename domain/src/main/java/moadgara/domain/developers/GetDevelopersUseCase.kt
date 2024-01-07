package moadgara.domain.developers

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.developers.entity.ListOfDevelopersResponse
import moadgara.data.developers.repository.DevelopersRepository

class GetDevelopersUseCase(
    private val repository: DevelopersRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<Unit, ListOfDevelopersResponse>(coroutineDispatcher) {
    override suspend fun execute(param: Unit): NetworkResult<ListOfDevelopersResponse> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page"] = "1"
        queryParams["page_size"] = "40"
        return repository.fetchDevelopers(queryParams)
    }
}