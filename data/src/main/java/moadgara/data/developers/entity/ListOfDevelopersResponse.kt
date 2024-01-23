package moadgara.data.developers.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moadgara.data.CommonResponseData
import moadgara.data.ResponseMapper

@Serializable
data class ListOfDevelopersResponse(
    @SerialName("count") val count: Int? = null,
    @SerialName("next") val next: String? = null,
    @SerialName("previous") val previous: String? = null,
    @SerialName("results") val results: List<DeveloperDetailResponse>? = null
) : ResponseMapper {
    override fun toSmallViewData() =
        results?.map { CommonResponseData(it.developerId, it.developerImageBackground, it.developerName) }

    override fun toImageList(): List<String>? = results?.mapNotNull { it.developerImageBackground }

    override fun rawResponse(): List<DeveloperDetailResponse>? = results
}