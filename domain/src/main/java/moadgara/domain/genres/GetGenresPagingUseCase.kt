package moadgara.domain.genres

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moadgara.common_model.usecase.PagingFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import moadgara.data.genres.entity.GenreDetailResponse
import moadgara.data.genres.repository.GenresRepository

class GetGenresPagingUseCase(
    private val genresRepository: GenresRepository,
    private val genresUseCase: GetGenresUseCase,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PagingFlowUseCase<Unit, GenreDetailResponse>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<PagingData<GenreDetailResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { GenresPagingSource(genresRepository, genresUseCase) }
        ).flow
    }

}