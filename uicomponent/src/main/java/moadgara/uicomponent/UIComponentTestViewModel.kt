package moadgara.uicomponent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import moadgara.base.validation.InputValidationError
import moadgara.base.validation.PhoneNumberTextWatcher

class UIComponentTestViewModel(
    val dialogNoParam: AlertDialog.Builder,
    val dialogNeutral: AlertDialog.Builder,
    val dialogPositiveNegative: AlertDialog.Builder,
    val dialogWithTitle: AlertDialog.Builder,
    val dialogWithDescription: AlertDialog.Builder,
    val dialogWarning: AlertDialog.Builder,
    val dialogError: AlertDialog.Builder,
    val dialogSuccess: AlertDialog.Builder,
    val dialogQuestion: AlertDialog.Builder,
    val dialogAnnouncement: AlertDialog.Builder
) : ViewModel() {

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