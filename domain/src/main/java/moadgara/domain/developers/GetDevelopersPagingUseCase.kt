package moadgara.domain.developers

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moadgara.common_model.usecase.PagingFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.developers.entity.DeveloperDetailResponse
import moadgara.data.developers.repository.DevelopersRepository

class GetDevelopersPagingUseCase(
    private val developersRepository: DevelopersRepository,
    private val developersUseCase: GetDevelopersUseCase,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PagingFlowUseCase<Unit, DeveloperDetailResponse>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<PagingData<DeveloperDetailResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { DevelopersPagingSource(developersRepository, developersUseCase) }
        ).flow
    }

}