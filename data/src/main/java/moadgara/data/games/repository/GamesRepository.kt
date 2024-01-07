package moadgara.data.games.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import moadgara.data.games.entity.ListOfGamesResponse

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
}