package com.moadgara.base.validation

import android.text.Editable
import android.text.TextWatcher
import com.moadgara.nexusnook.R

class PhoneNumberTextWatcher(
    private var successListener: () -> Unit,
    private var failureListener: (InputValidationError) -> Unit
) : TextWatcher {

    private var isTextBeingFormatted = false

    companion object {
        private val FAILURE_EMPTY = InputValidationError(R.string.phonenum_empty_error)
        private val FAILURE_LENGTH = InputValidationError(R.string.phonenum_length_error)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (s != null) {
            formatPhoneNumber(s)
        }

        val phoneText: String = s.toString()
        if (!isTextBeingFormatted) {
            when {
                phoneText.isBlank() -> failureListener(FAILURE_EMPTY)
                phoneText.length == 14 -> successListener()
                else -> failureListener(FAILURE_LENGTH)
            }
        }

    }

    private fun formatPhoneNumber(s: Editable) {
        if (s.isEmpty() || isTextBeingFormatted) {
            return
        }

        isTextBeingFormatted = true

        var i = 0
        while (i < s.length) {
            if ("0123456789".indexOf(s[i]) == -1) s.delete(i, i + 1)
            else i++
        }

        if (s.length >= 4) {
            s.insert(3, " ")
            s.insert(3, ")")
            s.insert(0, "(")
        }
        if (s.length > 9) {
            s.insert(9, "-")
        }

        isTextBeingFormatted = false
    }

}