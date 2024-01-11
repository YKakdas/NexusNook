package moadgara.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import moadgara.base.network.networkKoinModule
import moadgara.base.util.ContextProvider
import moadgara.base.util.ResourceProvider
import moadgara.main.discover.di.discoverKoinModule
import moadgara.main.games_detail.di.gameDetailsKoinModule
import moadgara.main.mainKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class NexusNookApplication : Application(), Application.ActivityLifecycleCallbacks,
    ContextProvider {

    private var currentActivity: Activity? = null
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        initLogger()
        initKoin()
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        currentActivity = activity
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        currentActivity = null
    }

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

    override fun getCurrentActivity() = currentActivity
    override fun getResourceProvider(): ResourceProvider {
        if (currentActivity?.applicationContext == null) {
            throw RuntimeException("Application context is null!!!")
        } else {
            return ResourceProvider(currentActivity!!.applicationContext)
        }
    }

    private fun initKoin() {
        val modules =
            listOf(globalKoinModule, networkKoinModule, mainKoinModule, discoverKoinModule, gameDetailsKoinModule)
        startKoin {
            androidContext(this@NexusNookApplication)
            modules(modules)
        }
    }

    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
    }

}