package moadgara.main.games_detail.di

import moadgara.base.util.ContextProvider
import moadgara.domain.games.GetGameDetailsFromIdUseCase
import moadgara.domain.games.GetScreenshotsFromGameIdUseCase
import moadgara.main.discover.DiscoverNavigator
import moadgara.main.games_detail.GameDetailsNavigator
import moadgara.main.games_detail.GameDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gameDetailsKoinModule = module {
    single { GameDetailsNavigator(get<ContextProvider>().getCurrentActivity()) }
    single { get<ContextProvider>().getResourceProvider() }
    single { GetGameDetailsFromIdUseCase(get()) }
    single { GetScreenshotsFromGameIdUseCase(get()) }

    viewModel { GameDetailsViewModel(get(), get(), get()) }
}