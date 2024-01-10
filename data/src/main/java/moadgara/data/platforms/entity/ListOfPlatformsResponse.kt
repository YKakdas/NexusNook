package moadgara.data.platforms.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moadgara.data.CommonResponseData
import moadgara.data.ResponseMapper

@Serializable
data class ListOfPlatformsResponse(
    @SerialName("count") val count: Int? = null,
    @SerialName("next") val next: String? = null,
    @SerialName("previous") val previous: String? = null,
    @SerialName("results") val results: List<PlatformDetailResponse>? = null
) : ResponseMapper {
    override fun toSmallViewData() =
        results?.map { CommonResponseData(it.platformId, it.platformImage ?: it.platformImageBackground, it.platformName) }

    override fun toImageList(): List<String>? = results?.mapNotNull { it.platformImage ?: it.platformImageBackground }
}

