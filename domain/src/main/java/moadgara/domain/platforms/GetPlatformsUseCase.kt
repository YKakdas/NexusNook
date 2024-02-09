package moadgara.domain.platforms

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.platforms.entity.ListOfPlatformsResponse
import moadgara.data.platforms.repository.PlatformsRepository

class GetPlatformsUseCase(
    private val repository: PlatformsRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<Unit, ListOfPlatformsResponse>(coroutineDispatcher) {
    override suspend fun execute(param: Unit): NetworkResult<ListOfPlatformsResponse> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page_size"] = "10"
        return repository.fetchPlatforms(queryParams)
    }
}