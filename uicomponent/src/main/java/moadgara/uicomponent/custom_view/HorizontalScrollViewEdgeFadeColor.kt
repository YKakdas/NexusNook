package moadgara.uicomponent.custom_view

import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import androidx.core.content.ContextCompat
import moadgara.uicomponent.R

class HorizontalScrollViewEdgeFadeColor(context: Context, attributeSet: AttributeSet?, defStyleInt: Int) :
    HorizontalScrollView(context, attributeSet, defStyleInt) {

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null, 0)

    override fun getSolidColor(): Int {
        return ContextCompat.getColor(context, R.color.black)
    }

}