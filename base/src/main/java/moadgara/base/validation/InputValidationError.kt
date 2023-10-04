package moadgara.base.validation

import android.content.res.Resources
import androidx.annotation.StringRes

/**
 * Generic class for handling error messages. Can be used in
 * [moadgara.uicomponent.CustomTextInputLayout] with [android.text.TextWatcher]
 *
 * @constructor creates an error message object with
 * @param [errorId] : [StringRes]
 * @param [errorText] : [String]
 *
 */
class InputValidationError(
    @StringRes
    private var errorId: Int? = null,
    private var errorText: String? = null,
    val isAlwaysVisible: Boolean = false
) {

    /**
     * If the [errorId] is given, such as R.id.error_text, fetches the value from the string resources
     * If the [errorText] is explicitly given, even if there is an errorId provided, override it and
     * use the given text
     * Otherwise, return an empty string
     */
    fun getText(resources: Resources): String {
        var error = ""

        errorId?.let { error = resources.getString(it) }
        errorText?.let { error = it }

        return error
    }
}