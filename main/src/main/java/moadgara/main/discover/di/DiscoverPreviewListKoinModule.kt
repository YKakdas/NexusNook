package moadgara.main.discover.di

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
import org.koin.dsl.module

val previewListModule = module {
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
}