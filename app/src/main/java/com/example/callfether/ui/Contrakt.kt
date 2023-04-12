package com.example.callfether.ui


import com.example.callfether.base.UiEvent

data class ViewState(
    val phoneNumber: String,
    val errorType: NumberErrors?
)
data class ViewStateCallScreen(val phoneNumber: String)

class OnButtonGoToCallScreen() : UiEvent
class OnTextChanged(val numberPhone: String) : UiEvent

