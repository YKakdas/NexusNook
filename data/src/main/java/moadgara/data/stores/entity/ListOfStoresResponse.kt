package moadgara.data.stores.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moadgara.data.CommonResponseData
import moadgara.data.ResponseMapper
import moadgara.data.publishers.entity.PublisherDetailResponse

@Serializable
data class ListOfStoresResponse(
    @SerialName("count") val count: Int? = null,
    @SerialName("next") val next: String? = null,
    @SerialName("previous") val previous: String? = null,
    @SerialName("results") val results: List<StoreDetailResponse>? = null
) : ResponseMapper {
    override fun toSmallViewData() = results?.map { CommonResponseData(it.storeId, it.storeImageBackground, it.storeName) }

    override fun toImageList(): List<String>? = results?.mapNotNull { it.storeImageBackground }

    override fun rawResponse(): List<StoreDetailResponse>?  = results
}