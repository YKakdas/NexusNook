package moadgara.main.paging.di

import moadgara.domain.creators.GetCreatorsPagingUseCase
import moadgara.domain.developers.GetDevelopersPagingUseCase
import moadgara.domain.games.GetGamesPagingUseCase
import moadgara.domain.genres.GetGenresPagingUseCase
import moadgara.domain.platforms.GetPlatformsPagingUseCase
import moadgara.domain.publishers.GetPublishersPagingUseCase
import moadgara.domain.stores.GetStoresPagingUseCase
import moadgara.domain.tags.GetTagsPagingUseCase
import org.koin.dsl.module

val pagingUseCaseModule = module {
    factory { params -> GetGamesPagingUseCase(get(), get(), params.get()) }

    single { GetCreatorsPagingUseCase(get(), get()) }
    single { GetDevelopersPagingUseCase(get(), get()) }
    single { GetGenresPagingUseCase(get(), get()) }
    single { GetPlatformsPagingUseCase(get(), get()) }
    single { GetPublishersPagingUseCase(get(), get()) }
    single { GetStoresPagingUseCase(get(), get()) }
    single { GetTagsPagingUseCase(get(), get()) }
}