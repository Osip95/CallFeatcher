package com.example.callfether.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.example.callfether.R
import com.example.callfether.presentation.MainViewModel
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var btnGoToScreenCall: Button
    private lateinit var etPhoneNumber: EditText
    private lateinit var inputLayoutPhoneNumber: TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnGoToScreenCall = findViewById(R.id.btnGoToCallScreen)
        etPhoneNumber = findViewById(R.id.EtPhoneNumber)
        inputLayoutPhoneNumber = findViewById(R.id.inputLayout)
        inputLayoutPhoneNumber.endIconMode = END_ICON_NONE

        mainViewModel.goCallScreenEvent.observe(this, ::goToCallScreenActivity)
        mainViewModel.viewState.observe(this,::render)

        etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainViewModel.processUIEvent(OnTextChanged(s.toString()))
               // val numberPhone = s.toString()
//                val onlyNumbers = numberPhone.all {
//                    it.isDigit()
//                }
//                if (!onlyNumbers || (s?.length != 10)) {
//                    inputLayoutPhoneNumber.isErrorEnabled = true
//                    inputLayoutPhoneNumber.error = getString(R.string.invalid_phone_number)
//                    btnGoToScreenCall.isEnabled = false
//                } else {
//                    btnGoToScreenCall.isEnabled = true
//                    inputLayoutPhoneNumber.isErrorEnabled = false
//                    inputLayoutPhoneNumber.endIconMode = END_ICON_CUSTOM
//                }
            }
        })

        btnGoToScreenCall.setOnClickListener {
            mainViewModel.processUIEvent(OnButtonGoToCallScreen(etPhoneNumber.text.toString()))
        }
    }

    private fun render(viewState: ViewState) {
        inputLayoutPhoneNumber.isErrorEnabled = viewState.isErrorEnabled
        inputLayoutPhoneNumber.error = viewState.errorTextInvalid
        btnGoToScreenCall.isEnabled = viewState.btnGoToScreenCallIsEnabled
        inputLayoutPhoneNumber.endIconMode = viewState.inputLayoutPhoneNumberEndIconMode
    }

    private fun goToCallScreenActivity(number: String) {
        val intent = Intent(this, CallScreenActivity::class.java).apply {
            putExtra(getString(R.string.phone_number_key), number)
        }
        startActivity(intent)
    }
}