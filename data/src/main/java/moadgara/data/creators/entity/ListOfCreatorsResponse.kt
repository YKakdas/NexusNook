package moadgara.data.creators.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moadgara.data.genres.entity.GenreDetailResponse

@Serializable
data class ListOfCreatorsResponse(
    @SerialName("count") val count: Int? = null,
    @SerialName("next") val next: String? = null,
    @SerialName("previous") val previous: String? = null,
    @SerialName("results") val results: List<CreatorDetailResponse>? = null
)