package moadgara.data.creators.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import kotlinx.coroutines.flow.Flow
import moadgara.data.creators.entity.ListOfCreatorsResponse
import moadgara.data.genres.entity.ListOfGenresResponse

class CreatorsRepository(private val networkInterface: NetworkInterface) {

    fun fetchCreators(queryParams: Map<String, String>): Flow<NetworkResult<ListOfCreatorsResponse>> {
        return networkInterface.getRequest("creators", queryParams)
    }

}