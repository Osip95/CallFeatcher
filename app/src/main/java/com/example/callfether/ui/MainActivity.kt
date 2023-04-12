package com.example.callfether.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.example.callfether.R
import com.example.callfether.presentation.MainViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val NO_ERROR = ""

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

        mainViewModel.goCallScreenEvent.observe(this, ::goToCallScreenActivity)
        mainViewModel.viewState.observe(this,::render)


        etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainViewModel.processUIEvent(OnTextChanged(s.toString()))
            }
        })

        btnGoToScreenCall.setOnClickListener {
            mainViewModel.processUIEvent(OnButtonGoToCallScreen())
        }
    }

    private fun render(viewState: ViewState) {
        when(viewState.errorType){
            NumberErrors.COMMON_ERROR -> {
                btnGoToScreenCall.isEnabled = false
                inputLayoutPhoneNumber.error = getString(R.string.common_error)
            }
            NumberErrors.TEN_CHARACTERS -> {
                btnGoToScreenCall.isEnabled = false
                inputLayoutPhoneNumber.error = getString(R.string.number_must_be_10_characters)
            }
            NumberErrors.DIGITS_ONLY -> {
                btnGoToScreenCall.isEnabled = false
                inputLayoutPhoneNumber.error = getString(R.string.number_must_be_digits_only)
            }
            NumberErrors.NO_ERROR -> {
                btnGoToScreenCall.isEnabled = true
                inputLayoutPhoneNumber.error = NO_ERROR
                inputLayoutPhoneNumber.endIconMode = TextInputLayout.END_ICON_CUSTOM
            }
            else -> {
                btnGoToScreenCall.isEnabled = false
                inputLayoutPhoneNumber.error = NO_ERROR
                inputLayoutPhoneNumber.endIconMode = TextInputLayout.END_ICON_NONE
            }
        }
    }

    private fun goToCallScreenActivity(number: String) {
        startActivity(CallScreenActivity.createIntent(this, number))
    }
}