package moadgara.domain.publishers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moadgara.common_model.network.NetworkResult
import moadgara.data.publishers.entity.PublisherDetailResponse
import moadgara.data.publishers.repository.PublishersRepository

class PublishersPagingSource(
    private val publishersRepository: PublishersRepository,
    private val publishersUseCase: GetPublishersUseCase,
) : PagingSource<String, PublisherDetailResponse>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PublisherDetailResponse> {
        val response = if (params.key == null) {
            publishersUseCase.invoke(Unit)
        } else {
            publishersRepository.fetchPublishersPage(params.key!!)
        }

        return when (response) {
            is NetworkResult.Success -> {
                val data = response.data
                LoadResult.Page(data = data?.results ?: emptyList(), prevKey = data?.previous, nextKey = data?.next)
            }

            is NetworkResult.Failure -> LoadResult.Error(Exception(response.message))
        }
    }


    override fun getRefreshKey(state: PagingState<String, PublisherDetailResponse>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

}