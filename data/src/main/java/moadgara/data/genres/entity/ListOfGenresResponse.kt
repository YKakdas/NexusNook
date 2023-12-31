package moadgara.data.genres.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListOfGenresResponse(
  @SerialName("count") val count: Int? = null,
  @SerialName("next") val next: String? = null,
  @SerialName("previous") val previous: String? = null,
  @SerialName("results") val results: List<GenreDetailResponse>? = null
)

