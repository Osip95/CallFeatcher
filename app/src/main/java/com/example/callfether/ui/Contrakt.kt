package com.example.callfether.ui

import com.example.callfether.ErrorCodes
import com.example.callfether.base.Event

data class ViewState(
    val numberPhone: String,
    var errorCode: ErrorCodes
)

data class ViewStateWind(val speedWind: String)

sealed class UiEvent(): Event {


}

class OnEditTextClicked(var number: String = ""): UiEvent()
class OnButtonGoToCallScreen(var number: String = "") : UiEvent()