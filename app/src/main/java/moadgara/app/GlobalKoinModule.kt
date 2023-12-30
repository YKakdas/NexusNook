package moadgara.app

import moadgara.base.util.ContextProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val globalKoinModule = module { single { androidApplication() as ContextProvider } }