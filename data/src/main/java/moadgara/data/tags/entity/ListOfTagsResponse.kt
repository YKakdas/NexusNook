package moadgara.data.tags.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moadgara.data.CommonResponseData
import moadgara.data.ResponseMapper

@Serializable
data class ListOfTagsResponse(
    @SerialName("count") val count: Int? = null,
    @SerialName("next") val next: String? = null,
    @SerialName("previous") val previous: String? = null,
    @SerialName("results") val results: List<TagDetailResponse>? = null
) : ResponseMapper {
    override fun toSmallViewData() = results?.map { CommonResponseData(it.tagId, it.tagImageBackground, it.tagName) }

    override fun toImageList(): List<String>? = results?.mapNotNull { it.tagImageBackground }
}