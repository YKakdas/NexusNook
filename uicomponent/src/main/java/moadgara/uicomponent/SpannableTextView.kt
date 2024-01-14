package moadgara.uicomponent

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

class SpannableTextView constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var firstSpanText: String? = null
    private var secondSpanText: String? = null
    private var firstSpanTextColor: Int = Color.WHITE
    private var secondSpanTextColor: Int = Color.WHITE
    private var firstSpanTextSize: Int = 16
    private var secondSpanTextSize: Int = 14

    init {
        parseDeclarableStyleAttributes(attrs)
        initSpan()
    }

    private fun parseDeclarableStyleAttributes(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.SpannableTextView).apply {
            firstSpanText = getString(R.styleable.SpannableTextView_firstSpanText)
            secondSpanText = getString(R.styleable.SpannableTextView_secondSpanText)
            firstSpanTextColor = getColor(R.styleable.SpannableTextView_firstSpanTextColor, Color.WHITE)
            secondSpanTextColor = getColor(R.styleable.SpannableTextView_secondSpanTextColor, Color.WHITE)
            firstSpanTextSize = getDimensionPixelSize(R.styleable.SpannableTextView_firstSpanTextSize, 16)
            secondSpanTextSize = getDimensionPixelSize(R.styleable.SpannableTextView_secondSpanTextSize, 14)

            recycle()
        }
    }

    fun setFirstSpanText(text: String) {
        firstSpanText = text
        initSpan()
    }

    fun setSecondSpanText(text: String?) {
        secondSpanText = text
        initSpan()
    }

    private fun initSpan() {
        firstSpanText?.let { firstSpan ->
            secondSpanText?.let { secondSpan ->
                val spannableStringBuilder = SpannableStringBuilder("$firstSpan\n$secondSpan")

                spannableStringBuilder.run {
                    setSpan(ForegroundColorSpan(firstSpanTextColor), 0, firstSpan.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    setSpan(AbsoluteSizeSpan(firstSpanTextSize), 0, firstSpan.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

                    val start = firstSpan.length + 1
                    val end = start + secondSpan.length

                    if (isWebsiteUrl(secondSpan)) {
                        val clickableSpan = object : ClickableSpan() {
                            override fun onClick(widget: View) {
                                val options = listOf("Open in WebView", "Open in Browser", "Copy link to clipboard")
                                alertDialog(context) {
                                    title("Please choose an action for the selected website link")
                                    options(options) {

                                    }
                                    cancelable(true)
                                    type(AlertDialog.Type.INFORMATION)
                                }
                            }
                        }
                        spannableStringBuilder.setSpan(clickableSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    }

                    setSpan(ForegroundColorSpan(secondSpanTextColor), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    setSpan(AbsoluteSizeSpan(secondSpanTextSize), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                }
                text = spannableStringBuilder
            }

        }
    }


    private fun isWebsiteUrl(text: String): Boolean {
        return android.util.Patterns.WEB_URL.matcher(text).matches()
    }


    private fun copyTextToClipboard(textToCopy: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clipData = android.content.ClipData.newPlainText("Copied Text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
    }

}