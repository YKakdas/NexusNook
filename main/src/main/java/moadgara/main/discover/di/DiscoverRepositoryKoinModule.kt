package moadgara.main.discover.di

import moadgara.data.creators.repository.CreatorsRepository
import moadgara.data.creators.repository.DevelopersRepository
import moadgara.data.games.repository.GamesRepository
import moadgara.data.genres.repository.GenresRepository
import moadgara.data.platforms.repository.PlatformsRepository
import moadgara.data.publishers.repository.PublishersRepository
import moadgara.data.stores.repository.StoresRepository
import moadgara.data.tags.repository.TagsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { GamesRepository(get()) }
    single { GenresRepository(get()) }
    single { PlatformsRepository(get()) }
    single { PublishersRepository(get()) }
    single { StoresRepository(get()) }
    single { CreatorsRepository(get()) }
    single { DevelopersRepository(get()) }
    single { TagsRepository(get()) }
}