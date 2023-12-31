package moadgara.domain.stores

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.stores.entity.ListOfStoresResponse
import moadgara.data.stores.repository.StoresRepository

class GetStoresUseCase(
    private val repository: StoresRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, ListOfStoresResponse>(coroutineDispatcher) {
    override fun execute(parameters: Unit): Flow<NetworkResult<ListOfStoresResponse>> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page"] = "1"
        queryParams["page_size"] = "40"
        return repository.fetchStores(queryParams)
    }
}