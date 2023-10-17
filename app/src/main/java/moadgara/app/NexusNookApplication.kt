package moadgara.app

import android.app.Application
import moadgara.base.network.koinNetworkModule
import org.koin.core.context.startKoin
import timber.log.Timber

class NexusNookApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        initKoin()
    }

    private fun initKoin() {
        val modules = listOf(koinNetworkModule)
        startKoin {
            modules(modules)
        }
    }
}