package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreDetailResponse(
    @SerialName("id") val storeId: Int? = null,
    @SerialName("name") val storeName: String? = null,
    @SerialName("slug") val storeSlug: String? = null,
    @SerialName("domain") val storeDomain: String? = null,
    @SerialName("games_count") val storeCount: Int? = null,
    @SerialName("image_background") val storeImageBackground: String? = null
)
