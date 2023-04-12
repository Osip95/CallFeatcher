package com.example.callfether.presentation


import androidx.lifecycle.LiveData
import com.example.callfether.base.BaseViewModel
import com.example.callfether.base.SingleLiveEvent
import com.example.callfether.base.UiEvent
import com.example.callfether.ui.NumberErrors
import com.example.callfether.ui.OnButtonGoToCallScreen
import com.example.callfether.ui.OnTextChanged
import com.example.callfether.ui.ViewState

private const val CORRECT_NUMBER_LENGTH = 10


class MainViewModel : BaseViewModel<ViewState>() {
    private val _goCallScreenEvent = SingleLiveEvent<String>()
    val goCallScreenEvent: LiveData<String> = _goCallScreenEvent
    override fun initialViewState(): ViewState = ViewState(
        phoneNumber = "",
        errorType = null
    )

    override fun reduce(event: UiEvent, previousState: ViewState): ViewState? {
        return when (event) {
            is OnButtonGoToCallScreen -> {
                _goCallScreenEvent.value = previousState.phoneNumber
                previousState.copy(phoneNumber = previousState.phoneNumber)
            }
            is OnTextChanged -> {
                val onlyNumbers = !event.numberPhone.all {
                    it.isDigit()
                }
                val tenSymbols = event.numberPhone.length != CORRECT_NUMBER_LENGTH
                val tenSymbolsAndOnlyNumbers = onlyNumbers && tenSymbols
                when (true) {
                    tenSymbolsAndOnlyNumbers -> previousState.copy(
                        errorType = NumberErrors.COMMON_ERROR
                    )
                    onlyNumbers -> previousState.copy(
                        errorType = NumberErrors.DIGITS_ONLY
                    )
                    tenSymbols -> previousState.copy(
                        errorType = NumberErrors.TEN_CHARACTERS
                    )
                    else -> previousState.copy(
                        phoneNumber = event.numberPhone,
                        errorType = NumberErrors.NO_ERROR
                    )
                }
            }
            else -> null
        }
    }
}