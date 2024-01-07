package moadgara.data.genres.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import moadgara.data.genres.entity.ListOfGenresResponse

class GenresRepository(private val networkInterface: NetworkInterface) {

    suspend fun fetchGenres(queryParams: Map<String, String>): NetworkResult<ListOfGenresResponse> {
        return networkInterface.getRequest("genres", queryParams)
    }

}