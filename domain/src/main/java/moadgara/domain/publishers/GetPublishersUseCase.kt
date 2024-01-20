package moadgara.domain.publishers

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.publishers.entity.ListOfPublishersResponse
import moadgara.data.publishers.repository.PublishersRepository

class GetPublishersUseCase(
    private val repository: PublishersRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<Unit, ListOfPublishersResponse>(coroutineDispatcher) {
    override suspend fun execute(param: Unit): NetworkResult<ListOfPublishersResponse> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page"] = "1"
        return repository.fetchPublishers(queryParams)
    }
}