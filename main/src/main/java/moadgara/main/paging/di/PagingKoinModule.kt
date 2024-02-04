package moadgara.main.paging.di

import moadgara.domain.games.GetPagingGamesUseCase
import moadgara.main.paging.GenericPagingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pagingKoinModule = module {

    viewModel { params ->
        GenericPagingViewModel(get { params }, get())
    }

    factory { params ->
        GetPagingGamesUseCase(get(), get(), params.get())
    }

}