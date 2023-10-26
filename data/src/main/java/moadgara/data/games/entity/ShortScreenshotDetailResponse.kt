package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShortScreenshotDetailResponse(
    @SerialName("id") val screenshotId: Int? = null,
    @SerialName("image") val screenshotImage: String? = null
)
