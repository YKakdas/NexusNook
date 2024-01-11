package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moadgara.data.platforms.entity.PlatformDetailResponse

@Serializable
data class MetacriticPlatformsResponse(
    @SerialName("metascore") val metascore: Int? = 0,
    @SerialName("url") val metacriticUrl: String? = null,
    @SerialName("platform") val metacriticPlatform: PlatformDetailResponse? = null
)
