package com.example.callfether.presentation

import androidx.lifecycle.LiveData
import com.example.callfether.ErrorCodes
import com.example.callfether.base.BaseViewModel
import com.example.callfether.base.Event
import com.example.callfether.base.SingleLiveEvent
import com.example.callfether.ui.OnButtonGoToCallScreen
import com.example.callfether.ui.OnEditTextClicked
import com.example.callfether.ui.UiEvent
import com.example.callfether.ui.ViewState

class MainViewModel:BaseViewModel<ViewState>() {
    private val _goCallEvent = SingleLiveEvent<String>()
    val goWindEvent: LiveData<String> = _goCallEvent
    override fun initialViewState(): ViewState = ViewState(numberPhone = "",
    errorCode = ErrorCodes.NO_ERROR)

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when(event) {
            is OnButtonGoToCallScreen -> {
                if(event.number == "") return previousState.copy(errorCode = ErrorCodes.NUMBER_PHONE_EMPTY)
                _goCallEvent.value = event.number
                return previousState.copy(numberPhone = event.number, errorCode = ErrorCodes.NO_ERROR)
            }
            else -> return null
        }
    }
}