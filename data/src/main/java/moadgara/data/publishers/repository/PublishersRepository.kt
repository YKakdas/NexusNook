package moadgara.data.publishers.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import kotlinx.coroutines.flow.Flow
import moadgara.data.publishers.entity.ListOfPublishersResponse

class PublishersRepository(private val networkInterface: NetworkInterface) {

    fun fetchPublishers(queryParams: Map<String, String>): Flow<NetworkResult<ListOfPublishersResponse>> {
        return networkInterface.getRequest("publishers", queryParams)
    }

}