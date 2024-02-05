package moadgara.uicomponent.custom_view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import androidx.databinding.BindingAdapter
import moadgara.base.extension.gone
import moadgara.base.extension.visible
import moadgara.uicomponent.R
import moadgara.uicomponent.databinding.LayoutExpandableTextBinding

class ExpandableTextLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private lateinit var binding: LayoutExpandableTextBinding

    private var collapsedMaxLines: Int = 3
    private var textSize: Int? = null
    private var textColor: ColorStateList? = null
    private var animationDuration: Int = 1200
    private var title: String? = null

    private var expandIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_expand_more)
    private var collapseIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_expand_less)

    init {
        parseDeclarableStyleAttributes(attrs)
        inflate()
        initViews()
        initClickListeners()
    }

    fun setDescriptionText(text: String?) {
        binding.expandableDescription.setBody(text)
        binding.run {
            expandableDescription.doOnLayout {
                if (expandableDescription.lineCount <= collapsedMaxLines) {
                    fadingBackground.gone()
                    button.gone()
                }
            }
        }
    }

    fun setOnAnimationChangeListener(animationChangeListener: ((Boolean, Int, Boolean) -> Unit)?) {
        binding.expandableDescription.setOnAnimationChangeListener(animationChangeListener)
    }

    private fun parseDeclarableStyleAttributes(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextLayout).apply {
            collapsedMaxLines = getInt(R.styleable.ExpandableTextLayout_collapsedMaxLines, 3)
            textSize = getDimensionPixelSize(R.styleable.ExpandableTextLayout_textSize, 18)
            textColor = getColorStateList(R.styleable.ExpandableTextLayout_textColor) ?: ColorStateList.valueOf(Color.WHITE)
            animationDuration = getInt(R.styleable.ExpandableTextLayout_animationDuration, 1200)
            title = getString(R.styleable.ExpandableTextLayout_title)
            recycle()
        }
    }

    private fun inflate() {
        binding = LayoutExpandableTextBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun initViews() {
        with(binding) {
            with(expandableDescription) {
                setCollapsedMaxTextLines(collapsedMaxLines)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, this@ExpandableTextLayout.textSize?.toFloat() ?: 8f)
                setTextColor(textColor)
                setAnimationDuration(animationDuration)
                setOnExpanded {
                    button.setImageDrawable(collapseIconDrawable)
                    fadingBackground.gone()
                }
                setOnCollapsed {
                    button.setImageDrawable(expandIconDrawable)
                    fadingBackground.visible()
                }
            }

            title.text = this@ExpandableTextLayout.title
        }
    }

    private fun initClickListeners() {
        with(binding) {
            expandableDescription.setOnClickListener { expandableDescription.toggle() }
            button.setOnClickListener { expandableDescription.toggle() }
        }
    }

}

@BindingAdapter("description")
fun setDescriptionText(view: ExpandableTextLayout, text: String?) {
    view.setDescriptionText(text)
}