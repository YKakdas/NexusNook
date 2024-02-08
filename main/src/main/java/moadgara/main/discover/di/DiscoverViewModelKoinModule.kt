package moadgara.main.discover.di

import moadgara.main.discover.DiscoverViewModel
import moadgara.main.discover.sublists.PreviewList
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val discoverViewModelModule = module {
    viewModel {
        DiscoverViewModel(
            enablePrefetch = true,
            prefetchAmount = 6,
            getKoin().getAll<PreviewList>()
        )
    }
}