package moadgara.data.publishers.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import moadgara.data.publishers.entity.ListOfPublishersResponse

class PublishersRepository(private val networkInterface: NetworkInterface) {

    suspend fun fetchPublishers(queryParams: Map<String, String>): NetworkResult<ListOfPublishersResponse> {
        return networkInterface.getRequest("publishers", queryParams)
    }

}