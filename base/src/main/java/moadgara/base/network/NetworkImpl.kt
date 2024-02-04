package moadgara.base.network

import com.moadgara.common_model.network.BaseUrl
import com.moadgara.common_model.network.NetworkInterface
import com.moadgara.common_model.network.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.reflect.TypeInfo
import moadgara.base.BuildConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass


class NetworkImpl : KoinComponent, NetworkInterface {
    private var isMock = false

    val client: HttpClient by inject { parametersOf(isMock) }

    override suspend fun <Response : Any> get(
        endPoint: String,
        type: KClass<Response>,
        queryParams: Map<String, String>?,
        directCall: Boolean
    ): NetworkResult<Response> {
        try {
            val queryParameters = queryParams?.toMutableMap() ?: mutableMapOf()
            queryParameters["key"] = BuildConfig.API_KEY

            val response = if (directCall) {
                client.get(endPoint)
            } else {
                client.get(BaseUrl.apiUrl + endPoint) {
                    queryParameters.forEach { parameter(it.key, it.value) }
                }
            }

            return if (response.status.value == 200) {
                NetworkResult.Success(response.body(TypeInfo(type = type, reifiedType = type.javaObjectType)))
            } else {
                NetworkResult.Failure(response.status.description)
            }
        } catch (e: Exception) {
            return NetworkResult.Failure(e.localizedMessage)
        }

    }

    //TODO(Update Post)
    suspend inline fun <reified Body : Any, reified Response> post(
        endPoint: String, requestBody: Body,
    ): NetworkResult<Response> = try {
        val response: Response =
            client.post(BaseUrl.apiUrl + endPoint) {
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }.body()

        NetworkResult.Success(response)
    } catch (e: Exception) {
        NetworkResult.Failure(e.message)
    }

}