package moadgara.main.discover

import moadgara.base.ContextProvider
import moadgara.data.games.repository.GamesRepository
import moadgara.domain.games.GetBestOfTheYearUseCase
import moadgara.domain.games.GetRecentlyAddedPopularGamesUseCase
import moadgara.domain.games.GetTrendingGamesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val discoverKoinModule = module {
    single { DiscoverNavigator(get<ContextProvider>().getCurrentActivity()) }
    
    single { GamesRepository(get()) }

    single { GetTrendingGamesUseCase(get()) }
    single { GetBestOfTheYearUseCase(get()) }
    single { GetRecentlyAddedPopularGamesUseCase(get()) }

    viewModel {
        DiscoverViewModel(
            get<ContextProvider>().getResourceProvider(),
            get(),
            get(),
            get(),
            get()
        )
    }
}