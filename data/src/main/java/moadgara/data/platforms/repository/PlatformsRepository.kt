package moadgara.data.platforms.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import kotlinx.coroutines.flow.Flow
import moadgara.data.platforms.entity.ListOfPlatformsResponse

class PlatformsRepository(private val networkInterface: NetworkInterface) {

    fun fetchPlatforms(queryParams: Map<String, String>): Flow<NetworkResult<ListOfPlatformsResponse>> {
        return networkInterface.getRequest("platforms", queryParams)
    }

}