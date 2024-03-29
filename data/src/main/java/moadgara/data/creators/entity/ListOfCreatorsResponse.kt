package moadgara.data.creators.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moadgara.data.CommonResponseData
import moadgara.data.ResponseMapper

@Serializable
data class ListOfCreatorsResponse(
    @SerialName("count") val count: Int? = null,
    @SerialName("next") val next: String? = null,
    @SerialName("previous") val previous: String? = null,
    @SerialName("results") val results: List<CreatorDetailResponse>? = null
) : ResponseMapper {
    override fun toSmallViewData() =
        results?.map { CommonResponseData(it.creatorId, it.creatorImage ?: it.creatorImageBackground, it.creatorName) }

    override fun toImageList(): List<String>? = results?.mapNotNull { it.creatorImage ?: it.creatorImageBackground }

    override fun rawResponse(): List<CreatorDetailResponse>? = results

}