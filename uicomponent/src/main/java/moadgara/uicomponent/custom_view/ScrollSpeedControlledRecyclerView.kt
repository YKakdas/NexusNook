package moadgara.uicomponent.custom_view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import moadgara.uicomponent.R

class ScrollSpeedControlledRecyclerView(context: Context, attrs: AttributeSet?, defStyleInt: Int) :
    RecyclerView(context, attrs, defStyleInt) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null, 0)

    private var verticalSpeedFactor = 1f
    private var horizontalSpeedFactor = 1f

    init {
        parseDeclarableStyleAttributes(attrs)
    }

    private fun parseDeclarableStyleAttributes(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.ScrollSpeedControlledRecyclerView).apply {
            verticalSpeedFactor = getFloat(R.styleable.ScrollSpeedControlledRecyclerView_verticalSpeedFactor, 1f)
            horizontalSpeedFactor = getFloat(R.styleable.ScrollSpeedControlledRecyclerView_horizontalSpeedFactor, 1f)
            recycle()
        }
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        return super.fling((velocityX * horizontalSpeedFactor).toInt(), (velocityY * verticalSpeedFactor).toInt())
    }
}