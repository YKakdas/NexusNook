package moadgara.base.network

import io.ktor.client.call.body
import io.ktor.client.engine.android.AndroidClientEngine
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import moadgara.base.BuildConfig

object NetworkInterface {

    val client =
        HttpClientConfig.createOrGetHttpClient(AndroidClientEngine(AndroidEngineConfig().apply {
            connectTimeout = 10000
            socketTimeout = 10000
        }))

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