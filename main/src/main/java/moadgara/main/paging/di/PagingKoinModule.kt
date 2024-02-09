package moadgara.main.paging.di

import org.koin.dsl.module

val pagingKoinModule = module {
    includes(pagingUseCaseModule, pagingViewModelModule)
}