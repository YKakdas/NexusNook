package moadgara.domain.stores

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.stores.entity.ListOfStoresResponse
import moadgara.data.stores.repository.StoresRepository

class GetStoresUseCase(
    private val repository: StoresRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<Unit, ListOfStoresResponse>(coroutineDispatcher) {
    override suspend fun execute(param: Unit): NetworkResult<ListOfStoresResponse> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page"] = "1"
        return repository.fetchStores(queryParams)
    }
}