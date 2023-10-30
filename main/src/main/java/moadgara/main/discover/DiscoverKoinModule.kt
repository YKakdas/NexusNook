package moadgara.main.discover

import moadgara.base.ContextProvider
import moadgara.data.games.repository.GamesRepository
import moadgara.domain.games.GetBestOfTheYearUseCase
import moadgara.domain.games.GetTrendingGamesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val discoverKoinModule = module {
    single { GetTrendingGamesUseCase(get()) }
    single { GetBestOfTheYearUseCase(get()) }
    single { GamesRepository(get()) }
    single { DiscoverNavigator(get<ContextProvider>().getCurrentActivity()) }
    viewModel {
        DiscoverViewModel(
            get<ContextProvider>().getResourceProvider(),
            get(),
            get(),
            get()
        )
    }
}