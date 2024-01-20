package moadgara.main.games_detail.di

import moadgara.base.util.ContextProvider
import moadgara.domain.games.GetGameDetailsFromIdUseCase
import moadgara.main.games_detail.GameDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gameDetailsKoinModule = module {
    single { get<ContextProvider>().getResourceProvider() }
    single { GetGameDetailsFromIdUseCase(get()) }

    viewModel { GameDetailsViewModel(get(), get()) }
}