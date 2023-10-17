package moadgara.base.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import moadgara.base.BuildConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf


class NetworkInterface : KoinComponent {
    private var isMock = false

    val client: HttpClient by inject { parametersOf(isMock) }

    suspend inline fun <reified T : Any> get(endPoint: String): Result<T> = try {
        val response: T = client.get(BaseUrl.apiUrl + endPoint) {
            url {
                parameters.append("key", SecurityUtil.decode(3, BuildConfig.API_KEY))
            }
        }.body()
        Result.Success(response)
    } catch (e: Exception) {
        Result.Failure(e.message)
    }

    suspend inline fun <reified Body : Any, reified Response> post(
        endPoint: String, requestBody: Body
    ): Result<Response> = try {
        val response: Response = client.post(BaseUrl.apiUrl + endPoint) {
            url {
                parameters.append("key", SecurityUtil.decode(3, BuildConfig.API_KEY))
            }
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.body()
        Result.Success(response)
    } catch (e: Exception) {
        Result.Failure(e.message)
    }

}