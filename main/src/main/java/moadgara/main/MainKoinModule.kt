package moadgara.main

import moadgara.base.CurrentActivityProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val mainKoinModule = module {
    single { MainNavigator(get()) }
}