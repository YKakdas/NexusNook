package moadgara.domain

enum class ListType(var order: Int, var additionalParameters: List<String>? = null) {
    TRENDING(0),
    ALL_TIME_TOP_250(1),
    BEST_OF_THE_YEAR(2),
    BEST_OF_LAST_YEAR(3),
    RECENTLY_ADDED_POPULAR(4),
    THIS_MONTH_RELEASED(5),
    THIS_WEEK_RELEASED(6),
    RELEASING_NEXT_WEEK(7),
    GENRES(8),
    PLATFORMS(9),
    PUBLISHERS(10),
    STORES(11),
    CREATORS(12),
    DEVELOPERS(13),
    TAGS(14)
}