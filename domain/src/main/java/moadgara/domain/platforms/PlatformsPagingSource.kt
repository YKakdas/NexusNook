package moadgara.domain.platforms

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moadgara.common_model.network.NetworkResult
import moadgara.data.platforms.entity.PlatformDetailResponse
import moadgara.data.platforms.repository.PlatformsRepository

class PlatformsPagingSource(
    private val platformsRepository: PlatformsRepository,
    private val platformsUseCase: GetPlatformsUseCase,
) : PagingSource<String, PlatformDetailResponse>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PlatformDetailResponse> {
        val response = if (params.key == null) {
            platformsUseCase.invoke(Unit)
        } else {
            platformsRepository.fetchPlatformPage(params.key!!)
        }

        return when (response) {
            is NetworkResult.Success -> {
                val data = response.data
                LoadResult.Page(data = data?.results ?: emptyList(), prevKey = data?.previous, nextKey = data?.next)
            }

            is NetworkResult.Failure -> LoadResult.Error(Exception(response.message))
        }
    }


    override fun getRefreshKey(state: PagingState<String, PlatformDetailResponse>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

}