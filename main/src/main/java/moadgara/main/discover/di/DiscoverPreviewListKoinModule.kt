package moadgara.main.discover.di

import moadgara.main.discover.sublists.games.BestOfTheYearGamesPreviewList
import moadgara.main.discover.sublists.creators.CreatorsPreviewList
import moadgara.main.discover.sublists.developers.DevelopersPreviewList
import moadgara.main.discover.sublists.genres.GenresPreviewList
import moadgara.main.discover.sublists.platforms.PlatformsPreviewList
import moadgara.main.discover.sublists.PreviewListCommonParameters
import moadgara.main.discover.sublists.publishers.PublishersPreviewList
import moadgara.main.discover.sublists.games.RecentlyAddedPopularGamesPreviewList
import moadgara.main.discover.sublists.games.ReleasingNextWeekGamesPreviewList
import moadgara.main.discover.sublists.stores.StoresPreviewList
import moadgara.main.discover.sublists.tags.TagsPreviewList
import moadgara.main.discover.sublists.games.ThisMonthReleasedGamesPreviewList
import moadgara.main.discover.sublists.games.ThisWeekReleasedGamesPreviewList
import moadgara.main.discover.sublists.games.TrendingGamesPreviewList
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