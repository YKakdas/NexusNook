package moadgara.domain.tags

import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moadgara.data.tags.entity.ListOfTagsResponse
import moadgara.data.tags.repository.TagsRepository

class GetTagsUseCase(
    private val repository: TagsRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<Unit, ListOfTagsResponse>(coroutineDispatcher) {
    override suspend fun execute(param: Unit): NetworkResult<ListOfTagsResponse> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["page"] = "1"
        return repository.fetchTags(queryParams)
    }
}