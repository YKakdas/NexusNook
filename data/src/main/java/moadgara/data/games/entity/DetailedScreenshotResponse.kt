package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailedScreenshotResponse(
    @SerialName("id") val screenshotId: Int? = null,
    @SerialName("image") val screenshotUrl: String? = null,
    @SerialName("width") val screenshotWidth: Int = 1920,
    @SerialName("height") val screenshotHeight: Int = 1080,
    @SerialName("is_deleted") val isScreenshotDeleted: Boolean = false
)
