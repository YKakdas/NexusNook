package moadgara.data.games.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import moadgara.data.games.entity.GameDetailsFromIdResponse
import moadgara.data.games.entity.ListOfGamesResponse
import moadgara.data.games.entity.ScreenshotsResponse

class GamesRepository(private val networkInterface: NetworkInterface) {
    suspend fun fetchTrendingGames(queryParams: Map<String, String>): NetworkResult<ListOfGamesResponse> {
        return networkInterface.getRequest("games/lists/main", queryParams)
    }

    suspend fun fetchBestOfTheYear(queryParams: Map<String, String>): NetworkResult<ListOfGamesResponse> {
        return networkInterface.getRequest("games/lists/greatest", queryParams)
    }

    suspend fun fetchRecentlyAddedPopularGames(queryParams: Map<String, String>): NetworkResult<ListOfGamesResponse> {
        return networkInterface.getRequest("games/lists/recent-games-past", queryParams)
    }

    suspend fun fetchGames(queryParams: Map<String, String>): NetworkResult<ListOfGamesResponse> {
        return networkInterface.getRequest("games", queryParams)
    }

    suspend fun fetchGameDetailsFromID(id: Int, queryParams: Map<String, String>): NetworkResult<GameDetailsFromIdResponse> {
        return networkInterface.getRequest("games/$id", queryParams)
    }

    suspend fun fetchScreenshotsFromID(id: Int, queryParams: Map<String, String>): NetworkResult<ScreenshotsResponse> {
        return networkInterface.getRequest("games/$id/screenshots", queryParams)
    }
}