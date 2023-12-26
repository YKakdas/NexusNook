package moadgara.uicomponent

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.TextView
import androidx.core.view.doOnLayout
import com.google.android.material.textfield.TextInputLayout
import moadgara.base.animation.AnimationDirection
import moadgara.base.animation.AnimationListenerParameters
import moadgara.base.animation.AnimationMetaData
import moadgara.base.animation.AnimationType
import moadgara.base.extension.closeSoftKeyboard
import moadgara.base.extension.getEnumOrDefault
import moadgara.base.extension.setAndStartAnimation
import moadgara.base.validation.InputValidationError


class CustomTextInputLayout constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    TextInputLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private var editTextColor: ColorStateList? = null
    private var hintColor: ColorStateList? = null
    private var errorColor: ColorStateList? = null
    private var boxStrokeColorOriginal: ColorStateList? = null
    private var boxStrokeColorError: ColorStateList? = null
    private var animationMetaDataShowError: AnimationMetaData = AnimationMetaData()
    private var animationMetaDataHideError: AnimationMetaData = AnimationMetaData()

    private lateinit var errorView: View
    private lateinit var errorTextView: TextView

    private var errorInitiated = false

    var validationError: InputValidationError? = null
        set(value) {
            field = value
            if (isErrorAlwaysVisible()) {
                showError()
            } else {
                clearError()
            }
        }

    init {
        parseDeclarableStyleAttributes(attrs)

        doOnLayout {
            editText?.onFocusChangeListener = focusChangeListener
            hintTextColor = hintColor
            errorView =
                LayoutInflater.from(context).inflate(R.layout.text_input_error_layout, this, false)
            errorView.visibility = GONE
            addView(errorView)
            // todo move these to custom attributes
            boxStrokeWidth = 5
            boxStrokeWidthFocused = 7
            errorTextView = errorView.findViewById(R.id.error_text)
        }

    }

    private val focusChangeListener = OnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            closeSoftKeyboard()
        }

        if ((hasFocus || validationError == null) && !isErrorAlwaysVisible()) {
            clearError()
        } else {
            showError()
        }

    }

    private fun parseDeclarableStyleAttributes(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomTextInputLayout).apply {
            editTextColor = getColorStateList(R.styleable.CustomTextInputLayout_editTextColor)
            hintColor = getColorStateList(R.styleable.CustomTextInputLayout_hintTextColor)
            errorColor = getColorStateList(R.styleable.CustomTextInputLayout_errorColor)
            boxStrokeColorOriginal =
                getColorStateList(R.styleable.CustomTextInputLayout_boxStrokeColorOriginal)
            boxStrokeColorError =
                getColorStateList(R.styleable.CustomTextInputLayout_boxStrokeColorOnError)

            animationMetaDataShowError = AnimationMetaData().apply {
                direction = getEnumOrDefault(
                    R.styleable.CustomTextInputLayout_animationDirectionShowError,
                    AnimationDirection.LEFT_TO_RIGHT
                )
                type = getEnumOrDefault(
                    R.styleable.CustomTextInputLayout_animationTypeShowError, AnimationType.FADE_IN
                )
                duration = getInt(
                    R.styleable.CustomTextInputLayout_animationDurationShowError, 1000
                ).toLong()
            }

            animationMetaDataHideError = AnimationMetaData().apply {
                direction = getEnumOrDefault(
                    R.styleable.CustomTextInputLayout_animationDirectionHideError,
                    AnimationDirection.LEFT_TO_RIGHT
                )
                type = getEnumOrDefault(
                    R.styleable.CustomTextInputLayout_animationTypeHideError, AnimationType.FADE_IN
                )
                duration = getInt(
                    R.styleable.CustomTextInputLayout_animationDurationHideError, 1000
                ).toLong()
            }

            recycle()
        }
    }

    private fun showError() {
        if (validationError != null) {
            errorTextView.text = validationError!!.getText(resources)
        }

        if (boxStrokeColorError != null) {
            setBoxStrokeColorStateList(boxStrokeColorError!!)
        }

        defaultHintTextColor = boxStrokeColorError
        editText?.setTextColor(errorColor)

        errorView.visibility = VISIBLE

        errorView.setAndStartAnimation(
            animationMetaDataShowError, AnimationListenerParameters(onEnd = {
                errorView.clearAnimation()
            })
        )

        errorInitiated = true
    }

    private fun clearError() {
        if (boxStrokeColorOriginal != null) {
            setBoxStrokeColorStateList(boxStrokeColorOriginal!!)
        }

        editText?.setTextColor(editTextColor)

        editText?.setTextColor(editTextColor)
        defaultHintTextColor = hintColor

        if (errorInitiated) {
            errorView.setAndStartAnimation(
                animationMetaDataHideError, AnimationListenerParameters(onEnd = {
                    errorView.clearAnimation()
                    errorView.visibility = GONE
                })
            )
            errorInitiated = false
        }

    }

    private fun isErrorAlwaysVisible(): Boolean {
        return validationError?.isAlwaysVisible ?: false
    }
}