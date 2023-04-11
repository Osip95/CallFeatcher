package com.example.callfether.presentation


import androidx.lifecycle.LiveData
import com.example.callfether.App
import com.example.callfether.NO_ERROR
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
                val onlyNumbers = !event.numberPhone.all {
                    it.isDigit()
                }
                val tenSymbols = event.numberPhone.length != 10
                val tenSymbolsAndOnlyNumbers = onlyNumbers && tenSymbols
                when (true) {
                    tenSymbolsAndOnlyNumbers -> previousState.copy(
                        isErrorEnabled = true,
                        errorTextInvalid = App.instance.getString(R.string.common_error),
                        btnGoToScreenCallIsEnabled = false
                    )
                    onlyNumbers -> previousState.copy(
                        isErrorEnabled = true,
                        errorTextInvalid = App.instance.getString(R.string.number_must_be_digits_only),
                        btnGoToScreenCallIsEnabled = false
                    )
                    tenSymbols -> previousState.copy(
                        isErrorEnabled = true,
                        errorTextInvalid = App.instance.getString(R.string.number_must_be_10_characters),
                        btnGoToScreenCallIsEnabled = false
                    )
                    else -> previousState.copy(
                        isErrorEnabled = false,
                        btnGoToScreenCallIsEnabled = true,
                        inputLayoutPhoneNumberEndIconMode = TextInputLayout.END_ICON_CUSTOM,
                        errorTextInvalid = NO_ERROR
                    )
                }
            }
            else -> null
        }
    }
}