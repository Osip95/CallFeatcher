package com.example.callfether.ui

import com.example.callfether.base.UiEvent

data class ViewState(
    val numberPhone: String
)

class OnButtonGoToCallScreen(var number: String = "") : UiEvent