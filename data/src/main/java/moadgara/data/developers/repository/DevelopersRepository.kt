package moadgara.data.creators.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import kotlinx.coroutines.flow.Flow
import moadgara.data.creators.entity.ListOfDevelopersResponse

class DevelopersRepository(private val networkInterface: NetworkInterface) {

    fun fetchDevelopers(queryParams: Map<String, String>): Flow<NetworkResult<ListOfDevelopersResponse>> {
        return networkInterface.getRequest("developers", queryParams)
    }

}