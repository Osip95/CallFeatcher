package com.example.callfether.ui


import com.example.callfether.base.UiEvent

data class ViewState(
    val phoneNumber: String,
    val validationState: PhoneValidationState
)

sealed class PhoneValidationState {
    object NotValidated : PhoneValidationState()
    data class NotValid(val errorType: PhoneErrorType) : PhoneValidationState()
    object Valid : PhoneValidationState()
}

enum class PhoneErrorType {
    TOO_SHORT,
    NOT_ALL_DIGITS
}
data class ViewStateCallScreen(val phoneNumber: String)

class OnButtonGoToCallScreen() : UiEvent
class OnTextChanged(val numberPhone: String) : UiEvent

