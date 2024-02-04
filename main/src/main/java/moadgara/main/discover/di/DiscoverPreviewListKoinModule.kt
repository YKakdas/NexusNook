package moadgara.main.discover.di

import moadgara.main.discover.sublists.PreviewList
import moadgara.main.discover.sublists.PreviewListCommonParameters
import moadgara.main.discover.sublists.creators.CreatorsPreviewList
import moadgara.main.discover.sublists.developers.DevelopersPreviewList
import moadgara.main.discover.sublists.games.BestOfTheLastYearGamesPreviewList
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
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.definition.Kind
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent

val previewListModule = module {
    single { PreviewListCommonParameters(get(), get()) }

    single { TrendingGamesPreviewList(get(), get()) } bind PreviewList::class
    single { BestOfTheYearGamesPreviewList(get(), get()) } bind PreviewList::class
    single { BestOfTheLastYearGamesPreviewList(get(), get()) } bind PreviewList::class
    single { RecentlyAddedPopularGamesPreviewList(get(), get()) } bind PreviewList::class
    single { ThisMonthReleasedGamesPreviewList(get(), get()) } bind PreviewList::class
    single { ThisWeekReleasedGamesPreviewList(get(), get()) } bind PreviewList::class
    single { ReleasingNextWeekGamesPreviewList(get(), get()) } bind PreviewList::class
    single { GenresPreviewList(get(), get()) } bind PreviewList::class
    single { PlatformsPreviewList(get(), get()) } bind PreviewList::class
    single { PublishersPreviewList(get(), get()) } bind PreviewList::class
    single { StoresPreviewList(get(), get()) } bind PreviewList::class
    single { CreatorsPreviewList(get(), get()) } bind PreviewList::class
    single { DevelopersPreviewList(get(), get()) } bind PreviewList::class
    single { TagsPreviewList(get(), get()) } bind PreviewList::class
}