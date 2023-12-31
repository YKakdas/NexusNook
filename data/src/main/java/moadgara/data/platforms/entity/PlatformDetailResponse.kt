package moadgara.data.platforms.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlatformDetailResponse(
  @SerialName("id") val platformId: Int? = null,
  @SerialName("slug") val platformSlug: String? = null,
  @SerialName("name") val platformName: String? = null,
  @SerialName("image") val platformImage: String? = null,
  @SerialName("year_end") val platformEndYear: Int? = null,
  @SerialName("year_start") val platformStartYear: Int? = null,
  @SerialName("games_count") val platformGamesCount: Int? = null,
  @SerialName("image_background") val platformImageBackground: String? = null
)
