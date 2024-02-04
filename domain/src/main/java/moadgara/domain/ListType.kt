package moadgara.domain

enum class ListType(val order: Int) {
    TRENDING(0),
    BEST_OF_THE_YEAR(1),
    BEST_OF_LAST_YEAR(2),
    RECENTLY_ADDED_POPULAR(3),
    THIS_MONTH_RELEASED(4),
    THIS_WEEK_RELEASED(5),
    RELEASING_NEXT_WEEK(6),
    GENRES(7),
    PLATFORMS(8),
    PUBLISHERS(9),
    STORES(10),
    CREATORS(11),
    DEVELOPERS(12),
    TAGS(13)
}