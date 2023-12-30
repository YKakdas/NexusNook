package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingDetailResponse(
  @SerialName("id") val ratingId: Int? = null,
  @SerialName("title") val ratingTitle: String? = null,
  @SerialName("count") val ratingCount: Int? = null,
  @SerialName("percent") val ratingPercent: Float? = null
)
