package moadgara.domain.creators

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.creators.entity.ListOfCreatorsResponse
import moadgara.data.creators.repository.CreatorsRepository

class GetCreatorsUseCase(
    private val repository: CreatorsRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, ListOfCreatorsResponse>(coroutineDispatcher) {
    override fun execute(parameters: Unit): Flow<NetworkResult<ListOfCreatorsResponse>> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page"] = "1"
        queryParams["page_size"] = "40"
        return repository.fetchCreators(queryParams)
    }
}