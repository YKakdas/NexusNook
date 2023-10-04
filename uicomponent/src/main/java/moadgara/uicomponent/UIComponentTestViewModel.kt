package moadgara.uicomponent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import moadgara.base.validation.InputValidationError
import moadgara.base.validation.PhoneNumberTextWatcher

class UIComponentTestViewModel : ViewModel() {

    val hintText = MutableLiveData("ViewModel setter hint text")

    val error = MutableLiveData<InputValidationError?>()

    private val phoneNumberInputSuccessListener = {
        error.value = null
    }

    private val phoneNumberInputFailureListener = { inputValidationError: InputValidationError ->
        error.value = inputValidationError
    }

    val phoneNumberTextWatcher =
        PhoneNumberTextWatcher(phoneNumberInputSuccessListener, phoneNumberInputFailureListener)


}