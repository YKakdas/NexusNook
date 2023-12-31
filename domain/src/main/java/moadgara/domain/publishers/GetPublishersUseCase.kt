package moadgara.domain.publishers

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.publishers.entity.ListOfPublishersResponse
import moadgara.data.publishers.repository.PublishersRepository

class GetPublishersUseCase(
    private val repository: PublishersRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, ListOfPublishersResponse>(coroutineDispatcher) {
    override fun execute(parameters: Unit): Flow<NetworkResult<ListOfPublishersResponse>> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page"] = "1"
        queryParams["page_size"] = "40"
        return repository.fetchPublishers(queryParams)
    }
}