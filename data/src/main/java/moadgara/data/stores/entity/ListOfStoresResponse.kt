package moadgara.data.stores.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListOfStoresResponse(
    @SerialName("count") val count: Int? = null,
    @SerialName("next") val next: String? = null,
    @SerialName("previous") val previous: String? = null,
    @SerialName("results") val results: List<StoreDetailResponse>? = null
)