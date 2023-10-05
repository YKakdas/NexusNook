package moadgara.uicomponent

import android.content.Context
import androidx.annotation.DrawableRes

class AlertDialog {

    class Builder(private val context: Context) {
        @DrawableRes
        private var dialogImage: Int = 0
    }
}