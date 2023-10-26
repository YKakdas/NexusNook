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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import moadgara.base.BuildConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass


class NetworkImpl : KoinComponent, NetworkInterface {
    private var isMock = false

    val client: HttpClient by inject { parametersOf(isMock) }

    override fun <T : Any> get(
        endPoint: String,
        queryParams: Map<String, String>?,
        type: KClass<T>
    ): Flow<NetworkResult<T>> = flow {
        emit(NetworkResult.Loading)
        try {
            val queryParameters = queryParams?.toMutableMap() ?: mutableMapOf()
            queryParameters["key"] = SecurityUtil.decode(3, BuildConfig.API_KEY)
            val response =
                client.get(BaseUrl.apiUrl + endPoint) {
                    queryParameters.forEach { parameter(it.key, it.value) }
                }
            if (response.status.value == 200) {
                emit(
                    NetworkResult.Success(
                        response.body(
                            TypeInfo(
                                type = type,
                                reifiedType = type.javaObjectType
                            )
                        )
                    )
                )
            } else {
                emit(NetworkResult.Failure(response.status.description))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Failure(e.localizedMessage))
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