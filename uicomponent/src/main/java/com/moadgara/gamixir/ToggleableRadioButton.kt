package com.moadgara.gamixir

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class ToggleableRadioButton constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatRadioButton(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun toggle() {
        isChecked = !isChecked
    }
}