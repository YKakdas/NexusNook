package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDetailResponse(
    @SerialName("id") val id: Int? = null,
    @SerialName("slug") val slug: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("released") val releasedDate: String? = null,
    @SerialName("tba") val tba: Boolean? = null,
    @SerialName("background_image") val backgroundImageUri: String? = null,
    @SerialName("rating") val rating: Float? = null,
    @SerialName("rating_top") val maxRatingScore: Int? = null,
    @SerialName("ratings") val ratings: List<RatingDetailResponse>? = null,
    @SerialName("ratings_count") val ratingsCount: Int? = null,
    @SerialName("reviews_text_count") val reviewsTextCount: Int? = null,
    @SerialName("added") val added: Int? = null,
    @SerialName("added_by_status") val addedByStatus: AddedByStatusResponse? = null,
    @SerialName("metacritic") val metaCritic: Int? = null,
    @SerialName("playtime") val playTime: Int? = null,
    @SerialName("suggestions_count") val suggestionsCount: Int? = null,
    @SerialName("updated") val updatedDate: String? = null,
    @SerialName("user_game") val userGame: String? = null,
    @SerialName("reviews_count") val reviewsCount: Int? = null,
    @SerialName("saturated_color") val saturatedColor: String? = null,
    @SerialName("dominant_color") val dominantColor: String? = null,
    @SerialName("esrb_rating") val esrbRating: EsrbRatingDetailResponse? = null,
    @SerialName("platforms") val platforms: List<PlatformMetaDataResponse>? = null,
    @SerialName("parent_platforms") val parentPlatforms: List<ParentPlatformMetaDataResponse>? = null,
    @SerialName("genres") val genres: List<GenreDetailResponse>? = null,
    @SerialName("stores") val stores: List<StoreMetaDataResponse>? = null,
    @SerialName("clip") val clip: String? = null,
    @SerialName("tags") val tags: List<TagDetailResponse>? = null,
    @SerialName("short_screenshots") val shortScreenshots: List<ShortScreenshotDetailResponse>? = null
)
