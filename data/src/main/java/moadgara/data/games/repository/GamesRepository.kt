package moadgara.data.games.repository

import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.network.getRequest
import kotlinx.coroutines.flow.Flow
import moadgara.data.games.entity.ListOfGamesResponse

class GamesRepository(private val networkInterface: NetworkInterface) {
    fun fetchTrendingGames(queryParams: Map<String, String>): Flow<NetworkResult<ListOfGamesResponse>> {
        return networkInterface.getRequest("games/lists/main", queryParams)
    }

    fun fetchBestOfTheYear(queryParams: Map<String, String>): Flow<NetworkResult<ListOfGamesResponse>> {
        return networkInterface.getRequest("games/lists/greatest", queryParams)
    }

    fun fetchRecentlyAddedPopularGames(queryParams: Map<String, String>): Flow<NetworkResult<ListOfGamesResponse>> {
        return networkInterface.getRequest("games/lists/recent-games-past", queryParams)
    }

    fun fetchGames(queryParams: Map<String, String>): Flow<NetworkResult<ListOfGamesResponse>> {
        return networkInterface.getRequest("games", queryParams)
    }
}