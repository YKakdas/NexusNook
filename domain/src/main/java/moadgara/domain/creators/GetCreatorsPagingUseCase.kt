package moadgara.domain.creators

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moadgara.common_model.usecase.PagingFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.creators.entity.CreatorDetailResponse
import moadgara.data.creators.repository.CreatorsRepository

class GetCreatorsPagingUseCase(
    private val creatorsRepository: CreatorsRepository,
    private val creatorsUseCase: GetCreatorsUseCase,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PagingFlowUseCase<Unit, CreatorDetailResponse>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<PagingData<CreatorDetailResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { CreatorsPagingSource(creatorsRepository, creatorsUseCase) }
        ).flow
    }

}