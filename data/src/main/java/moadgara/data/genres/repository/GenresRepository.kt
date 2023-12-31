package moadgara.data.genres.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import kotlinx.coroutines.flow.Flow
import moadgara.data.genres.entity.ListOfGenresResponse

class GenresRepository(private val networkInterface: NetworkInterface) {

    fun fetchGenres(queryParams: Map<String, String>): Flow<NetworkResult<ListOfGenresResponse>> {
        return networkInterface.getRequest("genres", queryParams)
    }

}