package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParentPlatformMetaDataResponse(
  @SerialName("platform") val parentPlatform: ParentPlatformDetailResponse? = null
)
