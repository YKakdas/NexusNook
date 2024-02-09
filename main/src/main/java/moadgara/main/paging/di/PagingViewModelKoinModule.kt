package moadgara.main.paging.di

import moadgara.main.paging.creators.CreatorsPagingViewModel
import moadgara.main.paging.developers.DevelopersPagingViewModel
import moadgara.main.paging.games.GamesPagingViewModel
import moadgara.main.paging.genres.GenresPagingViewModel
import moadgara.main.paging.platforms.PlatformsPagingViewModel
import moadgara.main.paging.publishers.PublishersPagingViewModel
import moadgara.main.paging.stores.StoresPagingViewModel
import moadgara.main.paging.tags.TagsPagingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pagingViewModelModule = module {
    viewModel { params -> GamesPagingViewModel(get { params }, get()) }

    viewModel { CreatorsPagingViewModel(get(), get(), get()) }
    viewModel { DevelopersPagingViewModel(get(), get(), get()) }
    viewModel { GenresPagingViewModel(get(), get(), get()) }
    viewModel { PlatformsPagingViewModel(get(), get(), get()) }
    viewModel { PublishersPagingViewModel(get(), get(), get()) }
    viewModel { StoresPagingViewModel(get(), get(), get()) }
    viewModel { TagsPagingViewModel(get(), get(), get()) }
}