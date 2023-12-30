package com.moadgara.common_model.network

import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

interface NetworkInterface {
    fun <T : Any> get(
      endPoint: String,
      queryParams: Map<String, String>? = null,
      type: KClass<T>
    ): Flow<NetworkResult<T>>

    //TODO(Add post)
}

