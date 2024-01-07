package moadgara.data.creators.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import moadgara.data.creators.entity.ListOfCreatorsResponse

class CreatorsRepository(private val networkInterface: NetworkInterface) {

    suspend fun fetchCreators(queryParams: Map<String, String>): NetworkResult<ListOfCreatorsResponse> {
        return networkInterface.getRequest("creators", queryParams)
    }

}