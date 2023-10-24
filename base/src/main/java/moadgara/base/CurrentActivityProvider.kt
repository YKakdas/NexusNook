package moadgara.base

import android.app.Activity

interface CurrentActivityProvider {
    fun getCurrentActivity(): Activity?
}