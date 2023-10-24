package moadgara.main

import moadgara.base.ContextProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainKoinModule = module {
    single { MainNavigator(get<ContextProvider>().getCurrentActivity()) }
    viewModel { MainViewModel(get(), get<ContextProvider>().getResourceProvider()) }
}