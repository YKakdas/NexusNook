package moadgara.data.stores.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreDetailResponse(
  @SerialName("id") val storeId: Int? = null,
  @SerialName("name") val storeName: String? = null,
  @SerialName("slug") val storeSlug: String? = null,
  @SerialName("domain") val storeDomain: String? = null,
  @SerialName("games_count") val storeGamesCount: Int? = null,
  @SerialName("image_background") val storeImageBackground: String? = null,
  @SerialName("games") val storeGames: List<StoreGame>? = null
)
