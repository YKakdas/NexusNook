package moadgara.main.discover.di

import moadgara.domain.creators.GetCreatorsUseCase
import moadgara.domain.developers.GetDevelopersUseCase
import moadgara.domain.games.GetGamesUseCase
import moadgara.domain.genres.GetGenresUseCase
import moadgara.domain.platforms.GetPlatformsUseCase
import moadgara.domain.publishers.GetPublishersUseCase
import moadgara.domain.stores.GetStoresUseCase
import moadgara.domain.tags.GetTagsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetGamesUseCase(get()) }
    single { GetGenresUseCase(get()) }
    single { GetPlatformsUseCase(get()) }
    single { GetPublishersUseCase(get()) }
    single { GetStoresUseCase(get()) }
    single { GetCreatorsUseCase(get()) }
    single { GetDevelopersUseCase(get()) }
    single { GetTagsUseCase(get()) }
}