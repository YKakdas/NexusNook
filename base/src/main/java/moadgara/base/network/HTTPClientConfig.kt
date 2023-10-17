package moadgara.base.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientConfig {
    private lateinit var httpClient: HttpClient

    fun createOrGetHttpClient(httpClientEngine: HttpClientEngine): HttpClient {
        return if (HttpClientConfig::httpClient.isInitialized) {
            httpClient
        } else {
            httpClient = HttpClient(httpClientEngine) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        encodeDefaults = false
                    })
                }
            }
            httpClient
        }
    }
}