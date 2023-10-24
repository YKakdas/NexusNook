package moadgara.base

import android.app.Activity

interface ContextProvider {
    fun getCurrentActivity(): Activity?
    fun getResourceProvider(): ResourceProvider
}