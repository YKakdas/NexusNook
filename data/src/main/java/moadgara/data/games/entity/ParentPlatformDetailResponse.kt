package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParentPlatformDetailResponse(
    @SerialName("id") val parentPlatformId: Int? = null,
    @SerialName("name") val parentPlatformName: String? = null,
    @SerialName("slug") val parentPlatformSlug: String? = null
)
