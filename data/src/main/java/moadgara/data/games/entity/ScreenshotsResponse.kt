package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScreenshotsResponse(
    @SerialName("count") val screenshotCount: Int? = null,
    @SerialName("next") val nextScreenshot: String? = null,
    @SerialName("previous") val previousScreenshot: String? = null,
    @SerialName("results") val screenshots: List<DetailedScreenshotResponse>? = null
)
