package com.moadgara.nexusnook

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.core.view.doOnLayout
import com.google.android.material.textfield.TextInputLayout
import com.moadgara.base.InputValidationError
import com.moadgara.base.animation.AnimationDirection
import com.moadgara.base.animation.AnimationListenerParameters
import com.moadgara.base.animation.AnimationMetaData
import com.moadgara.base.animation.AnimationType
import com.moadgara.base.closeSoftKeyboard
import com.moadgara.base.getEnumOrDefault
import com.moadgara.base.setAndStartAnimation


class CustomTextInputLayout constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    TextInputLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private var editTextColor: ColorStateList? = null
    private var hintColor: ColorStateList? = null
    private var errorColor: ColorStateList? = null
    private var boxStrokeColorOriginal: ColorStateList? = null
    private var boxStrokeColorError: ColorStateList? = null
    private var boxStrokeWidthOriginal: Int = 1
    private var animationMetaDataShowError: AnimationMetaData = AnimationMetaData()
    private var animationMetaDataHideError: AnimationMetaData = AnimationMetaData()

    private var isErrorInitialized = false
    var validationError: InputValidationError? = null

    private lateinit var errorView: View


    private val focusChangeListener = OnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            closeSoftKeyboard()
        }

        if (hasFocus /* || validationError == null */) {
            clearError()
        } else {
            showError()
        }

    }

    init {
        parseDeclarableStyleAttributes(attrs)

        doOnLayout {
            editText?.onFocusChangeListener = focusChangeListener
            hintTextColor = hintColor
            boxStrokeWidthOriginal = boxStrokeWidth
            errorView =
                LayoutInflater.from(context).inflate(R.layout.text_input_error_layout, this, false)
            errorView.visibility = GONE
            addView(errorView)
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
                    R.styleable.CustomTextInputLayout_animationTypeShowError,
                    AnimationType.FADE_IN
                )
                duration =
                    getInt(
                        R.styleable.CustomTextInputLayout_animationDurationShowError,
                        1000
                    ).toLong()
            }

            animationMetaDataHideError = AnimationMetaData().apply {
                direction = getEnumOrDefault(
                    R.styleable.CustomTextInputLayout_animationDirectionHideError,
                    AnimationDirection.LEFT_TO_RIGHT
                )
                type = getEnumOrDefault(
                    R.styleable.CustomTextInputLayout_animationTypeHideError,
                    AnimationType.FADE_IN
                )
                duration =
                    getInt(
                        R.styleable.CustomTextInputLayout_animationDurationHideError,
                        1000
                    ).toLong()
            }

            recycle()
        }
    }

    private fun showError() {
        if (boxStrokeColorError != null) {
            setBoxStrokeColorStateList(boxStrokeColorError!!)
        }

        boxStrokeWidth = 5
        defaultHintTextColor = boxStrokeColorError
        editText?.setTextColor(errorColor)

        errorView.visibility = VISIBLE

        errorView.setAndStartAnimation(
            animationMetaDataShowError, AnimationListenerParameters(onEnd = {
                errorView.clearAnimation()
            })
        )

    }

    private fun clearError() {
        if (boxStrokeColorOriginal != null) {
            setBoxStrokeColorStateList(boxStrokeColorOriginal!!)
        }

        editText?.setTextColor(editTextColor)
        boxStrokeWidth = boxStrokeWidthOriginal

        editText?.setTextColor(editTextColor)
        defaultHintTextColor = hintColor

        errorView.setAndStartAnimation(
            animationMetaDataHideError, AnimationListenerParameters(onEnd = {
                errorView.clearAnimation()
                errorView.visibility = GONE
            })
        )
    }
}