package com.moadgara.common_model.network

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T?) : NetworkResult<T>()
    data class Failure(val message: String?) : NetworkResult<Nothing>()
}