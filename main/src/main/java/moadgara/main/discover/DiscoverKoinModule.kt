package moadgara.main.discover

import moadgara.base.util.ContextProvider
import moadgara.data.creators.repository.CreatorsRepository
import moadgara.data.creators.repository.DevelopersRepository
import moadgara.data.games.repository.GamesRepository
import moadgara.data.genres.repository.GenresRepository
import moadgara.data.platforms.repository.PlatformsRepository
import moadgara.data.publishers.repository.PublishersRepository
import moadgara.data.stores.repository.StoresRepository
import moadgara.data.tags.repository.TagsRepository
import moadgara.domain.creators.GetCreatorsUseCase
import moadgara.domain.developers.GetDevelopersUseCase
import moadgara.domain.games.GetBestOfTheYearUseCase
import moadgara.domain.games.GetRecentlyAddedPopularGamesUseCase
import moadgara.domain.games.GetReleaseDateFilteredGamesUseCase
import moadgara.domain.games.GetTrendingGamesUseCase
import moadgara.domain.genres.GetGenresUseCase
import moadgara.domain.platforms.GetPlatformsUseCase
import moadgara.domain.publishers.GetPublishersUseCase
import moadgara.domain.stores.GetStoresUseCase
import moadgara.domain.tags.GetTagsUseCase
import moadgara.main.discover.sublists.BestOfTheYearGamesPreviewList
import moadgara.main.discover.sublists.CreatorsPreviewList
import moadgara.main.discover.sublists.DevelopersPreviewList
import moadgara.main.discover.sublists.GenresPreviewList
import moadgara.main.discover.sublists.PlatformsPreviewList
import moadgara.main.discover.sublists.PreviewListCommonParameters
import moadgara.main.discover.sublists.PublishersPreviewList
import moadgara.main.discover.sublists.RecentlyAddedPopularGamesPreviewList
import moadgara.main.discover.sublists.ReleasingNextWeekGamesPreviewList
import moadgara.main.discover.sublists.StoresPreviewList
import moadgara.main.discover.sublists.TagsPreviewList
import moadgara.main.discover.sublists.ThisMonthReleasedGamesPreviewList
import moadgara.main.discover.sublists.ThisWeekReleasedGamesPreviewList
import moadgara.main.discover.sublists.TrendingGamesPreviewList
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val discoverKoinModule = module {
    single { DiscoverNavigator(get<ContextProvider>().getCurrentActivity()) }
    single { get<ContextProvider>().getResourceProvider() }

    single { GamesRepository(get()) }
    single { GenresRepository(get()) }
    single { PlatformsRepository(get()) }
    single { PublishersRepository(get()) }
    single { StoresRepository(get()) }
    single { CreatorsRepository(get()) }
    single { DevelopersRepository(get()) }
    single { TagsRepository(get()) }

    single { GetTrendingGamesUseCase(get()) }
    single { GetBestOfTheYearUseCase(get()) }
    single { GetRecentlyAddedPopularGamesUseCase(get()) }
    factory { GetReleaseDateFilteredGamesUseCase(get()) }
    single { GetGenresUseCase(get()) }
    single { GetPlatformsUseCase(get()) }
    single { GetPublishersUseCase(get()) }
    single { GetStoresUseCase(get()) }
    single { GetCreatorsUseCase(get()) }
    single { GetDevelopersUseCase(get()) }
    single { GetTagsUseCase(get()) }

    single { PreviewListCommonParameters(get(), get()) }

    single { TrendingGamesPreviewList(get(), get()) }
    single { BestOfTheYearGamesPreviewList(get(), get()) }
    single { RecentlyAddedPopularGamesPreviewList(get(), get()) }
    single { ThisMonthReleasedGamesPreviewList(get(), get()) }
    single { ThisWeekReleasedGamesPreviewList(get(), get()) }
    single { ReleasingNextWeekGamesPreviewList(get(), get()) }
    single { GenresPreviewList(get(), get()) }
    single { PlatformsPreviewList(get(), get()) }
    single { PublishersPreviewList(get(), get()) }
    single { StoresPreviewList(get(), get()) }
    single { CreatorsPreviewList(get(), get()) }
    single { DevelopersPreviewList(get(), get()) }
    single { TagsPreviewList(get(), get()) }

    viewModel {
        DiscoverViewModel(
            listOf(
                get<TrendingGamesPreviewList>(),
                get<BestOfTheYearGamesPreviewList>(),
                get<RecentlyAddedPopularGamesPreviewList>(),
                get<ThisMonthReleasedGamesPreviewList>(),
                get<ThisWeekReleasedGamesPreviewList>(),
                get<ReleasingNextWeekGamesPreviewList>(),
                get<GenresPreviewList>(),
                get<PlatformsPreviewList>(),
                get<PublishersPreviewList>(),
                get<StoresPreviewList>(),
                get<CreatorsPreviewList>(),
                get<DevelopersPreviewList>(),
                get<TagsPreviewList>()
            )
        )
    }

}