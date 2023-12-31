package moadgara.data.tags.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import kotlinx.coroutines.flow.Flow
import moadgara.data.tags.entity.ListOfTagsResponse

class TagsRepository(private val networkInterface: NetworkInterface) {

    fun fetchTags(queryParams: Map<String, String>): Flow<NetworkResult<ListOfTagsResponse>> {
        return networkInterface.getRequest("tags", queryParams)
    }

}