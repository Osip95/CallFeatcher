package com.example.callfether.ui

import com.example.callfether.base.UiEvent

data class ViewState(
    val phoneNumber: String,
    val isErrorEnabled: Boolean,
    val errorTextInvalid: String,
    val btnGoToScreenCallIsEnabled: Boolean,
    val inputLayoutPhoneNumberEndIconMode: Int
)
data class ViewStateCallScreen(val phoneNumber: String)

class OnButtonGoToCallScreen(var number: String = "") : UiEvent
class OnTextChanged(val numberPhone: String) : UiEvent