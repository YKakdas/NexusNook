package com.moadgara.common_model.network

import kotlin.reflect.KClass

interface NetworkInterface {
    suspend fun <T : Any> get(
        endPoint: String,
        queryParams: Map<String, String>? = null,
        type: KClass<T>
    ): NetworkResult<T>

    //TODO(Add post)
}

