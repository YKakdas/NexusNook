package moadgara.data.publishers.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moadgara.data.CommonResponseData
import moadgara.data.ResponseMapper
import moadgara.data.platforms.entity.PlatformDetailResponse

@Serializable
data class ListOfPublishersResponse(
    @SerialName("count") val count: Int? = null,
    @SerialName("next") val next: String? = null,
    @SerialName("previous") val previous: String? = null,
    @SerialName("results") val results: List<PublisherDetailResponse>? = null
) : ResponseMapper {
    override fun toSmallViewData() =
        results?.map { CommonResponseData(it.publisherId, it.publisherImageBackground, it.publisherName) }

    override fun toImageList(): List<String>? = results?.mapNotNull { it.publisherImageBackground }

    override fun rawResponse(): List<PublisherDetailResponse>?  = results
}

