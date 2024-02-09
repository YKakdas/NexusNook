package moadgara.domain.tags

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moadgara.common_model.network.NetworkResult
import moadgara.data.tags.entity.TagDetailResponse
import moadgara.data.tags.repository.TagsRepository

class TagsPagingSource(
    private val tagsRepository: TagsRepository,
    private val tagsUseCase: GetTagsUseCase,
) : PagingSource<String, TagDetailResponse>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, TagDetailResponse> {
        val response = if (params.key == null) {
            tagsUseCase.invoke(Unit)
        } else {
            tagsRepository.fetchTagsPage(params.key!!)
        }

        return when (response) {
            is NetworkResult.Success -> {
                val data = response.data
                LoadResult.Page(data = data?.results ?: emptyList(), prevKey = data?.previous, nextKey = data?.next)
            }

            is NetworkResult.Failure -> LoadResult.Error(Exception(response.message))
        }
    }


    override fun getRefreshKey(state: PagingState<String, TagDetailResponse>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

}