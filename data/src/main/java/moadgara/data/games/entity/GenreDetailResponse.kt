package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDetailResponse(
    @SerialName("id") val genreId: Int? = null,
    @SerialName("name") val genreName: String? = null,
    @SerialName("slug") val genreSlug: String? = null,
    @SerialName("games_count") val genreGamesCount: Int? = null,
    @SerialName("image_background") val genreImageBackground: String? = null
)
