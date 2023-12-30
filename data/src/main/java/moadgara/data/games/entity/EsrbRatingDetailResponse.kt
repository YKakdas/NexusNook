package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EsrbRatingDetailResponse(
  @SerialName("id") val esrbId: Int? = null,
  @SerialName("slug") val esrbSlug: String? = null,
  @SerialName("name") val esrbName: String? = null
)
