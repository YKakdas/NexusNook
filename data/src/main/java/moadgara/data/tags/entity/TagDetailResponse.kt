package moadgara.data.tags.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagDetailResponse(
    @SerialName("id") val tagId: Int? = null,
    @SerialName("name") val tagName: String? = null,
    @SerialName("slug") val tagSlug: String? = null,
    @SerialName("language") val tagLanguage: String? = null,
    @SerialName("games_count") val tagGamesCount: Int? = null,
    @SerialName("image_background") val tagImageBackground: String? = null,
    @SerialName("games") val tagGames: List<TagGame>? = null
)
