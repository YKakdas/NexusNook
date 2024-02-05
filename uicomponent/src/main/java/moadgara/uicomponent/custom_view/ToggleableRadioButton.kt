package moadgara.uicomponent.custom_view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

/**
 * This custom radio button class makes radio button to be toggleable, i.e. it can be unchecked if
 * it is already checked.
 */
class ToggleableRadioButton constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
  AppCompatRadioButton(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun toggle() {
        isChecked = !isChecked
    }
}