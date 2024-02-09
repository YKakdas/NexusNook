package moadgara.data.developers.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeveloperDetailResponse(
    @SerialName("id") val developerId: Int? = null,
    @SerialName("name") val developerName: String? = null,
    @SerialName("slug") val developerSlug: String? = null,
    @SerialName("games_count") val developerGamesCount: Int? = null,
    @SerialName("image_background") val developerImageBackground: String? = null,
    @SerialName("games") val developerGames: List<DeveloperGame>? = null
)