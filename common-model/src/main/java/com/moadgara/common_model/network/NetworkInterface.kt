package com.moadgara.common_model.network

import kotlin.reflect.KClass

interface NetworkInterface {
    suspend fun <T : Any> get(
        endPoint: String,
        type: KClass<T>,
        queryParams: Map<String, String>? = null,
        directCall: Boolean = false
    ): NetworkResult<T>

    //TODO(Add post)
}

