package moadgara.data.tags.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagGame(
    @SerialName("id") val gameId: Int? = null,
    @SerialName("slug") val gameSlug: String? = null,
    @SerialName("name") val gameName: String? = null,
    @SerialName("added") val gameAddedCount: Int? = null,
)
