package moadgara.uicomponent.custom_view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.doOnEnd
import moadgara.base.setTextFromHtml

class ExpandableTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var state: State = State.COLLAPSED
    private var collapsedMaxLines: Int = 3
    private var animationDuration: Int = 1200
    private var collapsedHeight: Int = 0
    private var expandedHeight: Int = 0
    private var isBeingAnimated = false
    private var animationChangeListener: ((Boolean, Int, Boolean) -> Unit)? = null

    private var onExpanded: (() -> Unit)? = null
    private var onCollapsed: (() -> Unit)? = null

    fun setOnAnimationChangeListener(animationChangeListener: ((Boolean, Int, Boolean) -> Unit)?) {
        this.animationChangeListener = animationChangeListener
    }

    fun setCollapsedMaxTextLines(collapsedMaxLines: Int) {
        this.collapsedMaxLines = collapsedMaxLines
        if (state == State.COLLAPSED) {
            maxLines = collapsedMaxLines
        }
    }

    fun setAnimationDuration(animationDuration: Int) {
        this.animationDuration = animationDuration
    }

    fun setOnExpanded(onExpanded: (() -> Unit)?) {
        this.onExpanded = onExpanded
    }

    fun setOnCollapsed(onCollapsed: (() -> Unit)?) {
        this.onCollapsed = onCollapsed
    }

    fun setBody(text: String?) {
        setTextFromHtml(this, text.orEmpty())
    }

    internal fun toggle() {
        if (isBeingAnimated) return

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
            animate(expandedHeight, collapsedHeight)
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

    private fun animate(current: Int, new: Int) {
        val valueAnimator = ValueAnimator.ofInt(current, new).setDuration(animationDuration.toLong())
        var previous = current
        valueAnimator.addUpdateListener {
            val animatedValue = it.animatedValue as Int
            layoutParams.height = animatedValue
            val diff = animatedValue - previous
            previous = animatedValue
            animationChangeListener?.invoke(true, diff, isExpanded())
            requestLayout()
        }

        valueAnimator.doOnEnd {
            isBeingAnimated = false
            animationChangeListener?.invoke(false, 0, isExpanded())
            toggleEnd()
        }

        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.start()
        isBeingAnimated = true
        animationChangeListener?.invoke(true, 0, isExpanded())
    }

    private fun toggleEnd() {
        if (isExpanded()) {
            state = State.EXPANDED
            onExpanded?.invoke()
        } else {
            state = State.COLLAPSED
            maxLines = collapsedMaxLines
            onCollapsed?.invoke()
        }
    }

    private fun isExpanded(): Boolean = state == State.EXPANDED

}

enum class State {
    COLLAPSED,
    EXPANDED
}
