package moadgara.base.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientConfig {
    private lateinit var httpClient: HttpClient

    fun createOrGetHttpClient(isMock: Boolean): HttpClient {
        return if (isMock) {
            createMockHttpClient()
        } else {
            createAndroidHttpClient()
        }
    }

    private fun createAndroidHttpClient(): HttpClient {
        return if (HttpClientConfig::httpClient.isInitialized) {
            httpClient
        } else {
            httpClient = HttpClient(Android) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    })
                }

                install(HttpRequestRetry) {
                    retryOnServerErrors(maxRetries = 5)
                    exponentialDelay()
                }

                engine {
                    connectTimeout = 10000
                    socketTimeout = 10000
                }
            }
            httpClient
        }
    }

    private fun createMockHttpClient(): HttpClient {
        return if (HttpClientConfig::httpClient.isInitialized) {
            httpClient
        } else {
            httpClient = HttpClient(MockEngine) {
                engine {
                    // todo use addHandler to mock responses
                }
            }
            httpClient
        }

    }
}