package moadgara.main.discover.di

import moadgara.main.discover.DiscoverViewModel
import moadgara.main.discover.sublists.creators.CreatorsPreviewList
import moadgara.main.discover.sublists.developers.DevelopersPreviewList
import moadgara.main.discover.sublists.games.BestOfTheYearGamesPreviewList
import moadgara.main.discover.sublists.games.RecentlyAddedPopularGamesPreviewList
import moadgara.main.discover.sublists.games.ReleasingNextWeekGamesPreviewList
import moadgara.main.discover.sublists.games.ThisMonthReleasedGamesPreviewList
import moadgara.main.discover.sublists.games.ThisWeekReleasedGamesPreviewList
import moadgara.main.discover.sublists.games.TrendingGamesPreviewList
import moadgara.main.discover.sublists.genres.GenresPreviewList
import moadgara.main.discover.sublists.platforms.PlatformsPreviewList
import moadgara.main.discover.sublists.publishers.PublishersPreviewList
import moadgara.main.discover.sublists.stores.StoresPreviewList
import moadgara.main.discover.sublists.tags.TagsPreviewList
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        DiscoverViewModel(
            enablePrefetch = true,
            prefetchAmount = 6,
            previewLists = listOf(
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