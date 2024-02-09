package moadgara.domain.stores

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moadgara.common_model.usecase.PagingFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.stores.entity.StoreDetailResponse
import moadgara.data.stores.repository.StoresRepository

class GetStoresPagingUseCase(
    private val storesRepository: StoresRepository,
    private val storesUseCase: GetStoresUseCase,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PagingFlowUseCase<Unit, StoreDetailResponse>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<PagingData<StoreDetailResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { StoresPagingSource(storesRepository, storesUseCase) }
        ).flow
    }

}