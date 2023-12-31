package moadgara.main.discover

import moadgara.base.util.ContextProvider
import moadgara.base.util.DateUtil
import moadgara.base.util.ResourceProvider
import moadgara.data.games.repository.GamesRepository
import moadgara.domain.games.GetBestOfTheYearUseCase
import moadgara.domain.games.GetRecentlyAddedPopularGamesUseCase
import moadgara.domain.games.GetReleaseDateFilteredGamesUseCase
import moadgara.domain.games.GetTrendingGamesUseCase
import moadgara.main.discover.sublists.BestOfTheYearGamesPreviewList
import moadgara.main.discover.sublists.PreviewListCommonParameters
import moadgara.main.discover.sublists.RecentlyAddedPopularGamesPreviewList
import moadgara.main.discover.sublists.ReleasingNextWeekGamesPreviewList
import moadgara.main.discover.sublists.ThisMonthReleasedGamesPreviewList
import moadgara.main.discover.sublists.ThisWeekReleasedGamesPreviewList
import moadgara.main.discover.sublists.TrendingGamesPreviewList
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val discoverKoinModule = module {
    single { DiscoverNavigator(get<ContextProvider>().getCurrentActivity()) }
    single { get<ContextProvider>().getResourceProvider() }

    single { GamesRepository(get()) }

    single { GetTrendingGamesUseCase(get()) }
    single { GetBestOfTheYearUseCase(get()) }
    single { GetRecentlyAddedPopularGamesUseCase(get()) }
    factory { GetReleaseDateFilteredGamesUseCase(get()) }

    single { PreviewListCommonParameters(get(), get()) }

    single { TrendingGamesPreviewList(get(), get()) }
    single { BestOfTheYearGamesPreviewList(get(), get()) }
    single { RecentlyAddedPopularGamesPreviewList(get(), get()) }
    single { ThisMonthReleasedGamesPreviewList(get(), get()) }
    single { ThisWeekReleasedGamesPreviewList(get(), get()) }
    single { ReleasingNextWeekGamesPreviewList(get(), get()) }

    viewModel {
        DiscoverViewModel(
          listOf(
            get<TrendingGamesPreviewList>(),
            get<BestOfTheYearGamesPreviewList>(),
            get<RecentlyAddedPopularGamesPreviewList>(),
            get<ThisMonthReleasedGamesPreviewList>(),
            get<ThisWeekReleasedGamesPreviewList>(),
            get<ReleasingNextWeekGamesPreviewList>()
          )
        )
    }

}