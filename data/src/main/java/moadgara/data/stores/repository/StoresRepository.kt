package moadgara.data.stores.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import kotlinx.coroutines.flow.Flow
import moadgara.data.stores.entity.ListOfStoresResponse

class StoresRepository(private val networkInterface: NetworkInterface) {

    fun fetchStores(queryParams: Map<String, String>): Flow<NetworkResult<ListOfStoresResponse>> {
        return networkInterface.getRequest("stores", queryParams)
    }

}