package moadgara.base.extension

val String?.orEmpty: String get() = this ?: ""

fun String?.orDefault(default: String): String = this ?: default