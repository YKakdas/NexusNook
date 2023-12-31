package moadgara.data.platforms.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moadgara.data.games.entity.PlatformDetailResponse

@Serializable
data class ListOfPlatformsResponse(
    @SerialName("count") val count: Int? = null,
    @SerialName("next") val next: String? = null,
    @SerialName("previous") val previous: String? = null,
    @SerialName("results") val results: List<PlatformDetailResponse>? = null
)

