package com.example.callfether.presentation





import androidx.lifecycle.LiveData
import com.example.callfether.App
import com.example.callfether.R
import com.example.callfether.base.BaseViewModel
import com.example.callfether.base.SingleLiveEvent
import com.example.callfether.base.UiEvent
import com.example.callfether.ui.OnButtonGoToCallScreen
import com.example.callfether.ui.OnTextChanged
import com.example.callfether.ui.ViewState
import com.google.android.material.textfield.TextInputLayout

class MainViewModel : BaseViewModel<ViewState>() {
    private val _goCallScreenEvent = SingleLiveEvent<String>()
    val goCallScreenEvent: LiveData<String> = _goCallScreenEvent
    override fun initialViewState(): ViewState = ViewState(
        phoneNumber = "",
        isErrorEnabled = false,
        inputLayoutPhoneNumberEndIconMode = TextInputLayout.END_ICON_NONE,
        btnGoToScreenCallIsEnabled = false,
        errorTextInvalid = ""
    )

    override fun reduce(event: UiEvent, previousState: ViewState): ViewState? {
        return when (event) {
            is OnButtonGoToCallScreen -> {
                _goCallScreenEvent.value = event.number
                previousState.copy(phoneNumber = event.number)
            }
            is OnTextChanged -> {
                val onlyNumbers = event.numberPhone.all {
                    it.isDigit()
                }
                if (!onlyNumbers || (event.numberPhone.length != 10)) {
                    previousState.copy(isErrorEnabled = true,
                        errorTextInvalid = App.instance.getString(R.string.invalid_phone_number),
                        btnGoToScreenCallIsEnabled = false,
                    )
                } else {
                    previousState.copy(isErrorEnabled = false,
                        inputLayoutPhoneNumberEndIconMode = TextInputLayout.END_ICON_CUSTOM,
                        btnGoToScreenCallIsEnabled = true,
                        errorTextInvalid = ""
                    )
                }

            }
            else -> null
        }
    }
}