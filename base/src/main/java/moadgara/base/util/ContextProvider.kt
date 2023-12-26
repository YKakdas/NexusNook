package moadgara.base.util

import android.app.Activity

interface ContextProvider {
    fun getCurrentActivity(): Activity?
    fun getResourceProvider(): ResourceProvider
}