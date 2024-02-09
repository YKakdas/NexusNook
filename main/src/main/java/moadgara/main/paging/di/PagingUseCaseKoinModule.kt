package moadgara.main.paging.di

import moadgara.domain.games.GetGamesPagingUseCase
import moadgara.domain.platforms.GetPlatformsPagingUseCase
import org.koin.dsl.module

val pagingUseCaseModule = module {
    factory { params -> GetGamesPagingUseCase(get(), get(), params.get()) }
    factory { GetPlatformsPagingUseCase(get(), get()) }
}