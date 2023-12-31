package moadgara.data.stores.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreMetaDataResponse(
  @SerialName("id") val storesId: Int? = null,
  @SerialName("store") val store: StoreDetailResponse? = null
)
