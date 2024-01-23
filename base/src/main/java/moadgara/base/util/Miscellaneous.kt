package moadgara.base.util


inline fun <reified T> Any?.tryCast(): T? {
    return if (this is T) {
        this
    } else {
        null
    }
}

inline fun <reified T> Any?.tryCastNotNull(): T {
    return if (this is T) {
        this
    } else {
        throw ClassCastException()
    }
}