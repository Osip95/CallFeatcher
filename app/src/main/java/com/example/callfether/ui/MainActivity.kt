package com.example.callfether.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.example.callfether.ErrorCodes
import com.example.callfether.PHONE_NUMBER_KEY
import com.example.callfether.R
import com.example.callfether.presentation.MainViewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel = MainViewModel()
    private lateinit var btnGoToScreenCall: Button
    private lateinit var etPhoneNumber: EditText
    private lateinit var tvError: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnGoToScreenCall = findViewById(R.id.btnGoToCallScreen)
        etPhoneNumber = findViewById(R.id.EtPhoneNumber)
        tvError = findViewById(R.id.tvError)

        mainViewModel.viewState.observe(this, ::render)
        mainViewModel.goWindEvent.observe(this, ::goToCallScreenActivity)

        btnGoToScreenCall.setOnClickListener {
            mainViewModel.processUIEvent(OnButtonGoToCallScreen(etPhoneNumber.text.toString()))
        }
    }

    private fun render(viewState: ViewState) {
        tvError.text = when (viewState.errorCode) {
            ErrorCodes.NO_ERROR -> ""
            ErrorCodes.NUMBER_PHONE_EMPTY -> "ERROR"
        }
    }

    private fun goToCallScreenActivity(phoneNumber: String){
        val intent = Intent(this, CallScreenActivity::class.java).apply {
            putExtra(PHONE_NUMBER_KEY, phoneNumber)
        }
        startActivity(intent)
    }

}