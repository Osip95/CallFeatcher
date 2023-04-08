package com.example.callfether.presentation


import androidx.lifecycle.LiveData
import com.example.callfether.base.BaseViewModel
import com.example.callfether.base.SingleLiveEvent
import com.example.callfether.base.UiEvent
import com.example.callfether.ui.OnButtonGoToCallScreen
import com.example.callfether.ui.ViewState

class MainViewModel:BaseViewModel<ViewState>() {
    private val _goCallScrintEvent = SingleLiveEvent<String>()
    val goCallScrintEvent: LiveData<String> = _goCallScrintEvent
    override fun initialViewState(): ViewState = ViewState(numberPhone = "")
    override fun reduce(event: UiEvent, previousState: ViewState): ViewState? {
        return when(event) {
            is OnButtonGoToCallScreen -> {
                _goCallScrintEvent.value = event.number
                previousState.copy(numberPhone = event.number)
            }
            else -> null
        }
    }
}