package moadgara.main

import moadgara.base.util.ContextProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainKoinModule = module {
    factory { MainNavigator(get<ContextProvider>().getCurrentActivity()) }
    viewModel { MainViewModel(get()) }
}