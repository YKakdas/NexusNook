package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequirementDetailResponse(
    @SerialName("minimum") val minimum: String? = null,
    @SerialName("recommended") val recommended: String? = null
)
