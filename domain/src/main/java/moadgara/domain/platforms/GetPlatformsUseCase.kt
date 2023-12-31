package moadgara.domain.platforms

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.platforms.entity.ListOfPlatformsResponse
import moadgara.data.platforms.repository.PlatformsRepository

class GetPlatformsUseCase(
    private val repository: PlatformsRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, ListOfPlatformsResponse>(coroutineDispatcher) {
    override fun execute(parameters: Unit): Flow<NetworkResult<ListOfPlatformsResponse>> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page"] = "1"
        queryParams["page_size"] = "40"
        return repository.fetchPlatforms(queryParams)
    }
}