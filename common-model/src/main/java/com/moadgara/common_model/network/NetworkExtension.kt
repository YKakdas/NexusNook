package com.moadgara.common_model.network


suspend inline fun <reified T : Any> NetworkInterface.getRequest(
    endPoint: String,
    queryParams: Map<String, String>? = null,
    directCall: Boolean = false
) = get(endPoint, T::class, queryParams, directCall)