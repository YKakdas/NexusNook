package moadgara.domain.platforms

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moadgara.common_model.usecase.PagingFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.platforms.entity.PlatformDetailResponse
import moadgara.data.platforms.repository.PlatformsRepository

class GetPlatformsPagingUseCase(
    private val platformsRepository: PlatformsRepository,
    private val platformsUseCase: GetPlatformsUseCase,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PagingFlowUseCase<Unit, PlatformDetailResponse>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<PagingData<PlatformDetailResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { PlatformsPagingSource(platformsRepository, platformsUseCase) }
        ).flow
    }

}