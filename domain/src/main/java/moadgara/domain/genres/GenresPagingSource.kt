package moadgara.domain.genres

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moadgara.common_model.network.NetworkResult
import moadgara.data.genres.entity.GenreDetailResponse
import moadgara.data.genres.repository.GenresRepository

class GenresPagingSource(
    private val genresRepository: GenresRepository,
    private val genresUseCase: GetGenresUseCase,
) : PagingSource<String, GenreDetailResponse>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, GenreDetailResponse> {
        val response = if (params.key == null) {
            genresUseCase.invoke(Unit)
        } else {
            genresRepository.fetchGenresPage(params.key!!)
        }

        return when (response) {
            is NetworkResult.Success -> {
                val data = response.data
                LoadResult.Page(data = data?.results ?: emptyList(), prevKey = data?.previous, nextKey = data?.next)
            }

            is NetworkResult.Failure -> LoadResult.Error(Exception(response.message))
        }
    }


    override fun getRefreshKey(state: PagingState<String, GenreDetailResponse>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

}