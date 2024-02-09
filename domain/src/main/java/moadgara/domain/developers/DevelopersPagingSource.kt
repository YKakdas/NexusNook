package moadgara.domain.developers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moadgara.common_model.network.NetworkResult
import moadgara.data.developers.entity.DeveloperDetailResponse
import moadgara.data.developers.repository.DevelopersRepository

class DevelopersPagingSource(
    private val developersRepository: DevelopersRepository,
    private val developersUseCase: GetDevelopersUseCase,
) : PagingSource<String, DeveloperDetailResponse>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, DeveloperDetailResponse> {
        val response = if (params.key == null) {
            developersUseCase.invoke(Unit)
        } else {
            developersRepository.fetchDevelopersPage(params.key!!)
        }

        return when (response) {
            is NetworkResult.Success -> {
                val data = response.data
                LoadResult.Page(data = data?.results ?: emptyList(), prevKey = data?.previous, nextKey = data?.next)
            }

            is NetworkResult.Failure -> LoadResult.Error(Exception(response.message))
        }
    }


    override fun getRefreshKey(state: PagingState<String, DeveloperDetailResponse>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

}