package moadgara.main.paging.di

import moadgara.domain.games.GetPagingGamesUseCase
import moadgara.main.paging.games.GamesPagingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pagingKoinModule = module {

    viewModel { params ->
        GamesPagingViewModel(get { params }, get())
    }

    factory { params ->
        GetPagingGamesUseCase(get(), get(), params.get())
    }

}