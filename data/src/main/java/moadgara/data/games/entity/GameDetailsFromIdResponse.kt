package moadgara.data.games.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import moadgara.data.developers.entity.DeveloperDetailResponse
import moadgara.data.genres.entity.GenreDetailResponse
import moadgara.data.platforms.entity.ParentPlatformMetaDataResponse
import moadgara.data.platforms.entity.PlatformMetaDataResponse
import moadgara.data.publishers.entity.PublisherDetailResponse
import moadgara.data.stores.entity.StoreMetaDataResponse
import moadgara.data.tags.entity.TagDetailResponse

@Serializable
data class GameDetailsFromIdResponse(
    @SerialName("id") val id: Int? = null,
    @SerialName("slug") val slug: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("name_original") val originalName: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("metacritic") val metaCritic: Int? = null,
    @SerialName("metacritic_platforms") val metacriticPlatforms: List<MetacriticPlatformsResponse>? = null,
    @SerialName("released") val releasedDate: String? = null,
    @SerialName("tba") val tba: Boolean? = null,
    @SerialName("updated") val updatedDate: String? = null,
    @SerialName("background_image") val backgroundImageUri: String? = null,
    @SerialName("background_image_additional") var backgroundImageAdditionalUri: String? = null,
    @SerialName("website") val websiteUri: String? = null,
    @SerialName("rating") val rating: Float? = null,
    @SerialName("rating_top") val maxRatingScore: Int? = null,
    @SerialName("ratings") val ratings: List<RatingDetailResponse>? = null,
    @SerialName("reactions") val reactions: Map<Int, Int>? = null,
    @SerialName("added") val added: Int? = null,
    @SerialName("added_by_status") val addedByStatus: AddedByStatusResponse? = null,
    @SerialName("playtime") val playTime: Int? = null,
    @SerialName("screenshots_count") val screenshotsCount: Int? = null,
    @SerialName("movies_count") val moviesCount: Int? = null,
    @SerialName("creators_count") val creatorsCount: Int? = null,
    @SerialName("achievements_count") val achievementsCount: Int? = null,
    @SerialName("parent_achievements_count") val parentAchievementsCount: Int? = null,
    @SerialName("reddit_url") val redditUrl: String? = null,
    @SerialName("reddit_name") val redditName: String? = null,
    @SerialName("reddit_description") val redditDescription: String? = null,
    @SerialName("reddit_logo") val redditLogoUri: String? = null,
    @SerialName("reddit_count") val redditCount: Int? = null,
    @SerialName("twitch_count") val twitchCount: Int? = null,
    @SerialName("youtube_count") val youtubeCount: Int? = null,
    @SerialName("reviews_text_count") val reviewsTextCount: Int? = null,
    @SerialName("ratings_count") val ratingsCount: Int? = null,
    @SerialName("suggestions_count") val suggestionsCount: Int? = null,
    @SerialName("alternative_names") val alternativeNames: List<String>? = null,
    @SerialName("metacritic_url") val metacriticUrl: String? = null,
    @SerialName("parents_count") val parentsCount: Int? = null,
    @SerialName("additions_count") val additionsCount: Int? = null,
    @SerialName("game_series_count") val gameSeriesCount: Int? = null,
    @SerialName("user_game") val userGame: String? = null,
    @SerialName("reviews_count") val reviewsCount: Int? = null,
    @SerialName("saturated_color") val saturatedColor: String? = null,
    @SerialName("dominant_color") val dominantColor: String? = null,
    @SerialName("parent_platforms") val parentPlatforms: List<ParentPlatformMetaDataResponse>? = null,
    @SerialName("platforms") val platforms: List<PlatformMetaDataResponse>? = null,
    @SerialName("stores") val stores: List<StoreMetaDataResponse>? = null,
    @SerialName("developers") val developers: List<DeveloperDetailResponse>? = null,
    @SerialName("genres") val genres: List<GenreDetailResponse>? = null,
    @SerialName("tags") val tags: List<TagDetailResponse>? = null,
    @SerialName("publishers") val publishers: List<PublisherDetailResponse>? = null,
    @SerialName("esrb_rating") val esrbRating: EsrbRatingDetailResponse? = null,
    @SerialName("clip") val clip: String? = null,
    @SerialName("description_raw") val descriptionRaw: String? = null
)
