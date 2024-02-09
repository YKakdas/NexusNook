package moadgara.main.paging.di

import moadgara.main.paging.games.GamesPagingViewModel
import moadgara.main.paging.platforms.PlatformsPagingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pagingViewModelModule = module {
    viewModel { params -> GamesPagingViewModel(get { params }, get()) }
    viewModel { PlatformsPagingViewModel(get(), get(), get()) }
}