package moadgara.uicomponent

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import androidx.annotation.StringRes
import androidx.appcompat.view.ContextThemeWrapper
import moadgara.uicomponent.databinding.AlertDialogBinding
import moadgara.uicomponent.databinding.ButtonBinding

@DslMarker
annotation class AlertDialogBuilder

fun alertDialog(context: Context, builder: AlertDialog.Builder.() -> Unit): AlertDialog {
    return AlertDialog.Builder(context).apply(builder).build()
}

class AlertDialog {

    @AlertDialogBuilder
    class Builder(private val context: Context) : Cloneable {
        private var type: Type = Type.INFORMATION
        private var cancelable = false

        private var drawableRes: Int = R.drawable.dialog_information_background_image

        private var title: String? = null
        private var description: String? = null

        private var neutralText: String? = null
        private var positiveText: String? = null
        private var negativeText: String? = null

        private var neutralListener: (() -> Unit)? = null
        private var positiveListener: (() -> Unit)? = null
        private var negativeListener: (() -> Unit)? = null

        private var options: List<String>? = null
        private var optionClickListener: ((which: Int) -> Unit)? = null

        fun type(type: Type) = apply {
            this.type = type
            findDrawableImage(type)
        }

        fun cancelable(cancelable: Boolean) = apply { this.cancelable = cancelable }

        fun title(title: String) = apply { this.title = title }

        fun title(@StringRes title: Int) =
            apply { this.title = context.resources.getString(title) }

        fun getTitle() = title

        fun description(description: String) = apply { this.description = description }

        fun description(@StringRes description: Int) = apply {
            this.description = context.resources.getString(description)
        }

        fun getDescription() = description

        fun neutralText(text: String?) = apply { this.neutralText = text }

        fun neutralText(@StringRes text: Int) =
            apply { this.neutralText = context.resources.getString(text) }

        fun getNeutralText() = neutralText

        fun positiveText(text: String?) = apply { this.positiveText = text }

        fun positiveText(@StringRes text: Int) =
            apply { this.positiveText = context.resources.getString(text) }

        fun getPositiveText() = positiveText

        fun negativeText(text: String?) = apply { this.negativeText = text }

        fun negativeText(@StringRes text: Int) =
            apply { this.negativeText = context.resources.getString(text) }

        fun getNegativeText() = negativeText

        fun neutralListener(listener: (() -> Unit)?) = apply {
            this.neutralListener = listener
        }

        fun getNeutralListener() = neutralListener

        fun positiveListener(listener: (() -> Unit)?) = apply {
            this.positiveListener = listener
        }

        fun getPositiveListener() = positiveListener

        fun negativeListener(listener: (() -> Unit)?) = apply {
            this.negativeListener = listener
        }

        fun getNegativeListener() = negativeListener

        fun getDrawableRes() = drawableRes

        fun options(options: List<String>, listener: ((which: Int) -> Unit)?) = apply {
            this.options = options
            this.optionClickListener = listener
        }

        fun optionsFromResources(optionsResourceIds: List<Int>, listener: ((which: Int) -> Unit)?) = apply {
            this.options = optionsResourceIds.map { context.resources.getString(it) }
            this.optionClickListener = listener
        }

        internal fun build(): AlertDialog {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            try {
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val localInflater = dialog.layoutInflater.cloneInContext(
                ContextThemeWrapper(
                    context,
                    R.style.Theme_Main
                )
            )

            val binding = AlertDialogBinding.inflate(localInflater)
            binding.builder = this
            dialog.setContentView(binding.root)
            dialog.window!!.attributes.dimAmount = 0.85f
            dialog.setCancelable(cancelable)

            var isNeutral = false

            if (title.isNullOrBlank()) {
                binding.dialogTitle.visibility = View.GONE
            }

            if (description.isNullOrBlank()) {
                binding.dialogDescription.visibility = View.GONE
            }

            if (!neutralText.isNullOrBlank() || neutralListener != null) {
                isNeutral = true
                if (neutralText.isNullOrBlank()) {
                    neutralText =
                        context.resources.getString(R.string.alert_dialog_neutral_text_default)
                }
            }

            if (!isNeutral) {
                if (negativeText.isNullOrBlank()) {
                    binding.negativeButton.visibility = View.GONE
                }

                if (positiveText.isNullOrBlank()) {
                    positiveText =
                        context.resources.getString(R.string.alert_dialog_positive_text_default)
                }
                binding.neutralButton.visibility = View.GONE
            } else {
                binding.nonNeutralButtons.visibility = View.GONE
            }

            binding.neutralButton.setOnClickListener {
                dialog.cancel()
                neutralListener?.invoke()
            }

            binding.positiveButton.setOnClickListener {
                dialog.cancel()
                positiveListener?.invoke()
            }

            binding.negativeButton.setOnClickListener {
                dialog.cancel()
                negativeListener?.invoke()
            }

            findDrawableImage(type)

            if (options != null) {
                binding.neutralButton.visibility = View.GONE
                binding.nonNeutralButtons.visibility = View.GONE

                val optionsRoot = binding.buttonGroup
                optionsRoot.visibility = View.VISIBLE
                options?.forEachIndexed { index, optionText ->
                    val buttonBinding = ButtonBinding.inflate(localInflater, binding.buttonGroup, true)
                    buttonBinding.button.run {
                        text = optionText
                        setOnClickListener {
                            dialog.cancel()
                            optionClickListener?.invoke(index)
                        }
                    }
                }

            }

            dialog.show()
            return AlertDialog()
        }

        private fun findDrawableImage(type: Type) {
            drawableRes = when (type) {
                Type.INFORMATION -> R.drawable.dialog_information_background_image
                Type.WARNING -> R.drawable.dialog_warning_background_image
                Type.QUESTION -> R.drawable.dialog_question_background_image
                Type.ANNOUNCEMENT -> R.drawable.dialog_announcement_background_image
                Type.SUCCESS -> R.drawable.dialog_success_background_image
                Type.ERROR -> R.drawable.dialog_error_background_image
            }
        }

        public override fun clone(): Any {
            return super.clone()
        }

    }

    enum class Type {
        INFORMATION,
        WARNING,
        QUESTION,
        ANNOUNCEMENT,
        SUCCESS,
        ERROR
    }
}