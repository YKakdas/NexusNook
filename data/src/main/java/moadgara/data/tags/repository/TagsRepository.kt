package moadgara.data.tags.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import moadgara.data.tags.entity.ListOfTagsResponse

class TagsRepository(private val networkInterface: NetworkInterface) {

    suspend fun fetchTags(queryParams: Map<String, String>): NetworkResult<ListOfTagsResponse> {
        return networkInterface.getRequest("tags", queryParams)
    }

    suspend fun fetchTagsPage(pageUrl: String): NetworkResult<ListOfTagsResponse> {
        return networkInterface.getRequest(endPoint = pageUrl, directCall = true)
    }

}