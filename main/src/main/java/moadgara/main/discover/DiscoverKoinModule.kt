package moadgara.main.discover

import com.moadgara.common_model.usecase.FlowUseCase
import moadgara.base.ContextProvider
import moadgara.base.DateUtil
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.data.games.repository.GamesRepository
import moadgara.domain.games.GetBestOfTheYearUseCase
import moadgara.domain.games.GetRecentlyAddedPopularGamesUseCase
import moadgara.domain.games.GetReleaseDateFilteredGamesUseCase
import moadgara.domain.games.GetTrendingGamesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val discoverKoinModule = module {
    single { DiscoverNavigator(get<ContextProvider>().getCurrentActivity()) }

    single { GamesRepository(get()) }

    single<FlowUseCase<Unit, ListOfGamesResponse>>(named("TrendingGamesUseCase")) {
        GetTrendingGamesUseCase(get())
    }

    single<FlowUseCase<Unit, ListOfGamesResponse>>(named("BestOfTheYearUseCase")) {
        GetBestOfTheYearUseCase(get())
    }

    single<FlowUseCase<Unit, ListOfGamesResponse>>(named("RecentlyAddedPopularGamesUseCase")) {
        GetRecentlyAddedPopularGamesUseCase(get())
    }

    single<FlowUseCase<Pair<String, String>, ListOfGamesResponse>>(named("ReleaseDateFilteredGamesUseCase")) {
        GetReleaseDateFilteredGamesUseCase(get())
    }

    viewModel {
        DiscoverViewModel(
            get<ContextProvider>().getResourceProvider(),
            get(),
            listOf(
                Pair(get(named("TrendingGamesUseCase")), Unit),
                Pair(get(named("BestOfTheYearUseCase")), Unit),
                Pair(get(named("RecentlyAddedPopularGamesUseCase")), Unit),
                Pair(
                    get(named("ReleaseDateFilteredGamesUseCase")),
                    DateUtil.getDateRangeForMonth()
                ),
                Pair(
                    get(named("ReleaseDateFilteredGamesUseCase")),
                    DateUtil.getDateRangeForWeek(DateUtil.getCurrentWeek())
                ),
                Pair(
                    get(named("ReleaseDateFilteredGamesUseCase")),
                    DateUtil.getDateRangeForWeek(DateUtil.getCurrentWeek() + 1)
                )
            )
        )
    }

}