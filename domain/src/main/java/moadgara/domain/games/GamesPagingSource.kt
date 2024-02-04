package moadgara.domain.games

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moadgara.common_model.network.NetworkResult
import moadgara.data.games.entity.GameDetailResponse
import moadgara.data.games.repository.GamesRepository

class GamesPagingSource(
    private val gamesRepository: GamesRepository,
    private val gamesUseCase: GetGamesUseCase,
    private val gameListType: GameListType
) : PagingSource<String, GameDetailResponse>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, GameDetailResponse> {
        val response = if (params.key == null) {
            gamesUseCase.invoke(gameListType)
        } else {
            gamesRepository.fetchGamePage(params.key!!)
        }

        return when (response) {
            is NetworkResult.Success -> {
                val data = response.data
                LoadResult.Page(data = data?.results ?: emptyList(), prevKey = data?.previous, nextKey = data?.next)
            }

            is NetworkResult.Failure -> LoadResult.Error(Exception(response.message))
        }
    }


    override fun getRefreshKey(state: PagingState<String, GameDetailResponse>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

}