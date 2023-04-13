package com.example.callfether.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.callfether.R
import com.example.callfether.presentation.CallScreenViewModel



class CallScreenActivity : AppCompatActivity() {
   private lateinit var btnCallUp: Button
   private lateinit var tvPhoneNumber: TextView
   private lateinit var viewModelScreen: CallScreenViewModel

    companion object {
        private const val PHONE_NUMBER_KEY = "PHONE NUMBER KEY"
        fun createIntent(context: Context, number: String): Intent =
            Intent(context, CallScreenActivity::class.java).apply {
                putExtra(PHONE_NUMBER_KEY, number)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_screen)
        btnCallUp = findViewById(R.id.btnCallUp)
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber)
        val numberPhone = getString(R.string.formatted_phone_number,
            intent.getStringExtra(PHONE_NUMBER_KEY))
        viewModelScreen = CallScreenViewModel(numberPhone)
        viewModelScreen.viewStateCallScreen.observe(this,::setNumberPhone)
        btnCallUp.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + tvPhoneNumber.text.toString())
            startActivity(intent)
        }
    }

   private fun setNumberPhone(viewStateCallScreen: ViewStateCallScreen){
        tvPhoneNumber.text = viewStateCallScreen.phoneNumber
    }

}