package moadgara.main.discover.di

import moadgara.base.util.ContextProvider
import moadgara.main.discover.DiscoverNavigator
import org.koin.dsl.module

val discoverKoinModule = module {

    single { DiscoverNavigator(get<ContextProvider>().getCurrentActivity()) }
    single { get<ContextProvider>().getResourceProvider() }

    includes(discoverRepositoryModule, discoverUseCaseModule, discoverPreviewListModule, discoverViewModelModule)
}