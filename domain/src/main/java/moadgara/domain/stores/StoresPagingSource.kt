package moadgara.domain.stores

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moadgara.common_model.network.NetworkResult
import moadgara.data.stores.entity.StoreDetailResponse
import moadgara.data.stores.repository.StoresRepository

class StoresPagingSource(
    private val storesRepository: StoresRepository,
    private val storesUseCase: GetStoresUseCase,
) : PagingSource<String, StoreDetailResponse>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, StoreDetailResponse> {
        val response = if (params.key == null) {
            storesUseCase.invoke(Unit)
        } else {
            storesRepository.fetchStoresPage(params.key!!)
        }

        return when (response) {
            is NetworkResult.Success -> {
                val data = response.data
                LoadResult.Page(data = data?.results ?: emptyList(), prevKey = data?.previous, nextKey = data?.next)
            }

            is NetworkResult.Failure -> LoadResult.Error(Exception(response.message))
        }
    }


    override fun getRefreshKey(state: PagingState<String, StoreDetailResponse>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

}