package moadgara.domain.creators

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moadgara.common_model.network.NetworkResult
import moadgara.data.creators.entity.CreatorDetailResponse
import moadgara.data.creators.repository.CreatorsRepository

class CreatorsPagingSource(
    private val creatorsRepository: CreatorsRepository,
    private val creatorsUseCase: GetCreatorsUseCase,
) : PagingSource<String, CreatorDetailResponse>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, CreatorDetailResponse> {
        val response = if (params.key == null) {
            creatorsUseCase.invoke(Unit)
        } else {
            creatorsRepository.fetchCreatorsPage(params.key!!)
        }

        return when (response) {
            is NetworkResult.Success -> {
                val data = response.data
                LoadResult.Page(data = data?.results ?: emptyList(), prevKey = data?.previous, nextKey = data?.next)
            }

            is NetworkResult.Failure -> LoadResult.Error(Exception(response.message))
        }
    }


    override fun getRefreshKey(state: PagingState<String, CreatorDetailResponse>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

}