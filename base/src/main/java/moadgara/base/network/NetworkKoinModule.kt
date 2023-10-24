package moadgara.base.network

import org.koin.dsl.module

val networkKoinModule = module {
    single { params -> HttpClientConfig.createOrGetHttpClient(params.get()) }
    single { NetworkInterface() }
}