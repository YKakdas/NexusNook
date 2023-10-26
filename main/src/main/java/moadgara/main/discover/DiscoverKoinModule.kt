package moadgara.main.discover

import moadgara.data.games.repository.GamesRepository
import moadgara.domain.games.GetTrendingGamesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val discoverKoinModule = module {
    single { GetTrendingGamesUseCase(get()) }
    single { GamesRepository(get()) }
    viewModel { DiscoverViewModel(get()) }
}