package com.moadgara.common_model.network


inline fun <reified T : Any> NetworkInterface.getRequest(
    endPoint: String,
    queryParams: Map<String, String>? = null
) = get(endPoint, queryParams, T::class)