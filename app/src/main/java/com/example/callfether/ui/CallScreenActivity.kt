package com.example.callfether.ui

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_screen)
        btnCallUp = findViewById(R.id.btnCallUp)
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber)
        val numberPhone = getString(R.string.formatted_phone_number,
            intent.getStringExtra(getString(R.string.phone_number_key)))
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