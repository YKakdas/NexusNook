package moadgara.data.stores.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import moadgara.data.stores.entity.ListOfStoresResponse

class StoresRepository(private val networkInterface: NetworkInterface) {

    suspend fun fetchStores(queryParams: Map<String, String>): NetworkResult<ListOfStoresResponse> {
        return networkInterface.getRequest("stores", queryParams)
    }

}