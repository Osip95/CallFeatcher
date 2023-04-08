package com.example.callfether.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<VIEW_STATE> : ViewModel() {

    val viewState: MutableLiveData<VIEW_STATE> by lazy { MutableLiveData(initialViewState()) }
    abstract fun initialViewState(): VIEW_STATE
    abstract fun reduce(event: UiEvent, previousState: VIEW_STATE): VIEW_STATE?
    fun processUIEvent(event: UiEvent) {
        updateState(event)
    }



    private fun updateState(event: UiEvent) {
        val newViewState = reduce(event, viewState.value ?: initialViewState())
        if (newViewState != null && newViewState != viewState.value) {
            viewState.value = newViewState
        }
    }

}