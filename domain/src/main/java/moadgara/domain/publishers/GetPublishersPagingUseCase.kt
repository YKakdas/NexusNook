package moadgara.domain.publishers

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moadgara.common_model.usecase.PagingFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.publishers.entity.PublisherDetailResponse
import moadgara.data.publishers.repository.PublishersRepository

class GetPublishersPagingUseCase(
    private val publishersRepository: PublishersRepository,
    private val publishersUseCase: GetPublishersUseCase,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PagingFlowUseCase<Unit, PublisherDetailResponse>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<PagingData<PublisherDetailResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { PublishersPagingSource(publishersRepository, publishersUseCase) }
        ).flow
    }

}