package moadgara.uicomponent

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import moadgara.base.extension.toPx
import moadgara.base.extension.toSp

class MetascoreView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : AppCompatButton(context, attrs, defStyleAttr) {

    private var score = -1

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        gravity = Gravity.CENTER
        isClickable = false
        isFocusable = false
        textSize = 24.toSp
        setTypeface(typeface, Typeface.BOLD)
        setTextColor(ContextCompat.getColorStateList(context, R.color.metascore_color_selector))
        background = ContextCompat.getDrawable(context, R.drawable.metascore_background_shape)

        parseDeclarableStyleAttributes(attrs)
    }

    private fun parseDeclarableStyleAttributes(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.MetascoreView).apply {
            val size = getDimensionPixelSize(R.styleable.MetascoreView_android_textSize, 24.toPx)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
            recycle()
        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val states = super.onCreateDrawableState(extraSpace + 1)
        when (score) {
            in 75..100 -> mergeDrawableStates(states, intArrayOf(R.attr.state_high_score))
            in 50..74 -> mergeDrawableStates(states, intArrayOf(R.attr.state_average_score))
            in 0..49 -> mergeDrawableStates(states, intArrayOf(R.attr.state_low_score))
        }

        return states
    }

    fun setScore(score: Int) {
        this.score = score
        text = score.toString()
        refreshDrawableState()
    }

}

@BindingAdapter("score")
fun setScore(view: MetascoreView, score: Int) {
    view.setScore(score)
}