package moadgara.uicomponent

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.doOnEnd

class ExpandableTextView constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var state: State = State.COLLAPSED
    private var collapsedMaxLines: Int = 3
    private var textSize: Int? = null
    private var textColor: ColorStateList? = null
    private var body: String? = null

    private var animationDuration: Int = 1200

    private var collapsedHeight: Int = 0
    private var expandedHeight: Int = 0

    private var isBeingAnimated = false

    init {
        parseDeclarableStyleAttributes(attrs)
        setupTextView()
        setOnClickListener { if (!isBeingAnimated) toggle() }
    }

    private fun parseDeclarableStyleAttributes(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView).apply {
            collapsedMaxLines = getInt(R.styleable.ExpandableTextView_collapsedMaxLines, 3)
            textSize = getDimensionPixelSize(R.styleable.ExpandableTextView_textSize, 18)
            textColor = getColorStateList(R.styleable.ExpandableTextView_textColor) ?: ColorStateList.valueOf(Color.WHITE)
            body = getString(R.styleable.ExpandableTextView_body)
            animationDuration = getInt(R.styleable.ExpandableTextView_animationDuration, 1200)

            recycle()
        }
    }

    private fun setupTextView() {
        setTextColor(textColor)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, this@ExpandableTextView.textSize?.toFloat() ?: 8f)
        text = body
        if (state == State.COLLAPSED) {
            maxLines = collapsedMaxLines
        }
    }

    private fun toggle() {
        if (expandedHeight == 0) {
            measureExpandedHeight()
        }
        if (collapsedHeight == 0) {
            measureCollapsedHeight()
        }

        state = if (state == State.COLLAPSED) {
            maxLines = lineCount
            animate(collapsedHeight, expandedHeight)
            State.EXPANDED
        } else {
            animate(expandedHeight, collapsedHeight) {
                maxLines = collapsedMaxLines
            }
            State.COLLAPSED
        }
    }

    private fun measureCollapsedHeight() {
        val previousMaxLines = maxLines
        maxLines = collapsedMaxLines
        collapsedHeight = measureHeight()
        maxLines = previousMaxLines
    }

    private fun measureExpandedHeight() {
        val previousMaxLines = maxLines
        maxLines = lineCount
        expandedHeight = measureHeight()
        maxLines = previousMaxLines

    }

    private fun measureHeight(): Int {
        measure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        return measuredHeight
    }

    private fun animate(current: Int, new: Int, callback: (() -> Unit)? = null) {
        val valueAnimator = ValueAnimator.ofInt(current, new).setDuration(animationDuration.toLong())
        valueAnimator.addUpdateListener {
            layoutParams.height = it.animatedValue as Int
            requestLayout()
        }

        valueAnimator.doOnEnd {
            callback?.invoke()
            isBeingAnimated = false
        }

        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.start()
        isBeingAnimated = true
    }

    fun setBody(text: String?) {
        this.text = text
    }

}

enum class State {
    COLLAPSED,
    EXPANDED
}
