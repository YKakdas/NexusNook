package moadgara.main.discover.di

import moadgara.main.discover.DiscoverViewModel
import moadgara.main.discover.sublists.BestOfTheYearGamesPreviewList
import moadgara.main.discover.sublists.CreatorsPreviewList
import moadgara.main.discover.sublists.DevelopersPreviewList
import moadgara.main.discover.sublists.GenresPreviewList
import moadgara.main.discover.sublists.PlatformsPreviewList
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

val viewModelModule = module {
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