package com.example.callfether.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.callfether.ui.ViewStateCallScreen

class CallScreenViewModel(val phoneNumber: String): ViewModel() {
 val viewStateCallScreen: MutableLiveData<ViewStateCallScreen> =
     MutableLiveData(ViewStateCallScreen(phoneNumber = phoneNumber))
}
