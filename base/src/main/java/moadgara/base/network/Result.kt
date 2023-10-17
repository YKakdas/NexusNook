package moadgara.base.network

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val error: String?) : Result<Nothing>()
}