package moadgara.base.network

import org.koin.dsl.module

val koinNetworkModule = module {
    single { params -> HttpClientConfig.createOrGetHttpClient(params.get()) }
    single { NetworkInterface() }
}