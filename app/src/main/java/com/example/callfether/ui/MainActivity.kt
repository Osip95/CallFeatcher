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

        mainViewModel.goCallScreenEvent.observe(this, ::goToCallScreen)
        mainViewModel.viewState.observe(this, ::render)


        etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainViewModel.processUIEvent(OnTextChanged(s.toString()))
            }
        })

        btnGoToScreenCall.setOnClickListener {
            mainViewModel.processUIEvent(OnButtonGoToCallScreen())
        }
    }

    private fun render(viewState: ViewState) {
        when (viewState.validationState) {
            PhoneValidationState.NotValidated -> {
                btnGoToScreenCall.isEnabled = false
                inputLayoutPhoneNumber.error = null
                inputLayoutPhoneNumber.endIconMode = TextInputLayout.END_ICON_NONE
            }

            is PhoneValidationState.NotValid -> {
                val errorText = when (viewState.validationState.errorType) {
                    PhoneErrorType.TOO_SHORT -> getString(R.string.number_must_be_10_characters)
                    PhoneErrorType.NOT_ALL_DIGITS -> getString(R.string.number_must_be_digits_only)
                }
                inputLayoutPhoneNumber.error = errorText
                btnGoToScreenCall.isEnabled = false
                inputLayoutPhoneNumber.endIconMode = TextInputLayout.END_ICON_CUSTOM
            }
            PhoneValidationState.Valid -> {
                btnGoToScreenCall.isEnabled = true
                inputLayoutPhoneNumber.error = null
                inputLayoutPhoneNumber.endIconMode = TextInputLayout.END_ICON_CUSTOM
            }
        }
    }

        private fun goToCallScreen(number: String) {
            startActivity(CallScreenActivity.createIntent(this, number))
        }
    }