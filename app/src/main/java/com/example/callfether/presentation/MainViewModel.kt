package com.example.callfether.presentation


import androidx.lifecycle.LiveData
import com.example.callfether.base.BaseViewModel
import com.example.callfether.base.SingleLiveEvent
import com.example.callfether.base.UiEvent
import com.example.callfether.ui.*

private const val CORRECT_NUMBER_LENGTH = 10


class MainViewModel : BaseViewModel<ViewState>() {
    private val _goCallScreenEvent = SingleLiveEvent<String>()
    val goCallScreenEvent: LiveData<String> = _goCallScreenEvent
    override fun initialViewState(): ViewState = ViewState(
        phoneNumber = "",
        validationState = PhoneValidationState.NotValidated
    )

    override fun reduce(event: UiEvent, previousState: ViewState): ViewState? {
        return when (event) {
            is OnButtonGoToCallScreen -> {
                _goCallScreenEvent.value = previousState.phoneNumber
                null
            }
            is OnTextChanged -> {
                val phoneNumber = event.numberPhone
                val onlyDigits = phoneNumber.all { it.isDigit() }
                val tooShort = phoneNumber.length < CORRECT_NUMBER_LENGTH
                val validationState = when {
                    phoneNumber.isEmpty() -> PhoneValidationState.NotValidated
                    !onlyDigits           -> PhoneValidationState.NotValid(PhoneErrorType.NOT_ALL_DIGITS)
                    tooShort              -> PhoneValidationState.NotValid(PhoneErrorType.TOO_SHORT)
                    else                  -> PhoneValidationState.Valid
                }
                previousState.copy(
                    phoneNumber = event.numberPhone,
                    validationState = validationState
                )

            }
            else -> null
        }
    }
}