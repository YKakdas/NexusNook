package moadgara.data.platforms.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moadgara.data.games.entity.RequirementDetailResponse

@Serializable
data class PlatformMetaDataResponse(
    @SerialName("platform") val platform: PlatformDetailResponse? = null,
    @SerialName("released_at") val releasedAt: String? = null,
    @SerialName("requirements_en") val requirementsEnglish: RequirementDetailResponse? = null,
    @SerialName("requirements_ru") val requirementsRussian: RequirementDetailResponse? = null
)
