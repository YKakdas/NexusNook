package moadgara.data.developers.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import moadgara.data.developers.entity.ListOfDevelopersResponse

class DevelopersRepository(private val networkInterface: NetworkInterface) {

    suspend fun fetchDevelopers(queryParams: Map<String, String>): NetworkResult<ListOfDevelopersResponse> {
        return networkInterface.getRequest("developers", queryParams)
    }

}