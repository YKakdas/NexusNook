package moadgara.data.platforms.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import moadgara.data.platforms.entity.ListOfPlatformsResponse

class PlatformsRepository(private val networkInterface: NetworkInterface) {

    suspend fun fetchPlatforms(queryParams: Map<String, String>): NetworkResult<ListOfPlatformsResponse> {
        return networkInterface.getRequest("platforms", queryParams)
    }

}