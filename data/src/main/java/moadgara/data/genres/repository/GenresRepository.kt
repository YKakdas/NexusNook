package moadgara.data.genres.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import moadgara.data.genres.entity.ListOfGenresResponse
import moadgara.data.platforms.entity.ListOfPlatformsResponse

class GenresRepository(private val networkInterface: NetworkInterface) {

    suspend fun fetchGenres(queryParams: Map<String, String>): NetworkResult<ListOfGenresResponse> {
        return networkInterface.getRequest("genres", queryParams)
    }

    suspend fun fetchGenresPage(pageUrl: String): NetworkResult<ListOfGenresResponse> {
        return networkInterface.getRequest(endPoint = pageUrl, directCall = true)
    }

}