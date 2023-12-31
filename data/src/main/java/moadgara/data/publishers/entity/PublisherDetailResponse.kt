package moadgara.data.publishers.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PublisherDetailResponse(
    @SerialName("id") val publisherId: Int? = null,
    @SerialName("name") val publisherName: String? = null,
    @SerialName("slug") val publisherSlug: String? = null,
    @SerialName("games_count") val publisherGamesCount: Int? = null,
    @SerialName("image_background") val publisherImageBackground: String? = null
)