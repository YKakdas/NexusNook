package moadgara.data.creators.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatorDetailResponse(
    @SerialName("id") val creatorId: Int? = null,
    @SerialName("name") val creatorName: String? = null,
    @SerialName("slug") val creatorSlug: String? = null,
    @SerialName("image") val creatorImage: String? = null,
    @SerialName("games_count") val creatorGamesCount: Int? = null,
    @SerialName("image_background") val creatorImageBackground: String? = null
)