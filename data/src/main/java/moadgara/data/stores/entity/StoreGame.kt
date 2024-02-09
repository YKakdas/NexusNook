package moadgara.data.stores.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreGame(
    @SerialName("id") val gameId: Int? = null,
    @SerialName("slug") val gameSlug: String? = null,
    @SerialName("name") val gameName: String? = null,
    @SerialName("added") val gameAddedCount: Int? = null,
)
