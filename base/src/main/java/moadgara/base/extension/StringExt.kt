package moadgara.base.extension

fun String?.orEmpty(): String = this ?: ""

fun String?.orDefault(default: String): String = this ?: default