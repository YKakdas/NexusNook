package moadgara.base.network

import com.moadgara.common_model.network.NetworkInterface
import org.koin.dsl.module

val networkKoinModule = module {
    single { params -> HttpClientConfig.createOrGetHttpClient(params.get()) }
    single<NetworkInterface> { NetworkImpl() }
}