package moadgara.data.stores.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moadgara.data.ResponseMapper
import moadgara.data.CommonResponseData

@Serializable
data class ListOfStoresResponse(
    @SerialName("count") val count: Int? = null,
    @SerialName("next") val next: String? = null,
    @SerialName("previous") val previous: String? = null,
    @SerialName("results") val results: List<StoreDetailResponse>? = null
) : ResponseMapper {
    override fun toSmallViewData() = results?.map { CommonResponseData(it.storeImageBackground, it.storeName) }
}