package moadgara.domain.tags

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moadgara.common_model.usecase.PagingFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.tags.entity.TagDetailResponse
import moadgara.data.tags.repository.TagsRepository

class GetTagsPagingUseCase(
    private val tagsRepository: TagsRepository,
    private val tagsUseCase: GetTagsUseCase,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PagingFlowUseCase<Unit, TagDetailResponse>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<PagingData<TagDetailResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { TagsPagingSource(tagsRepository, tagsUseCase) }
        ).flow
    }

}