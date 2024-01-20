package moadgara.base.extension

fun <T> MutableList<T>.addIfConditionMet(item: T, condition: () -> Boolean) {
    if (condition.invoke()) {
        add(item)
    }
}

fun <T> MutableList<T>.addIfNotNull(item: T?) {
    if (item != null) add(item)
}

fun <T> MutableList<T>.addIfNotNull(item: T, nullCheck: Any?) {
    if (nullCheck != null) add(item)
}