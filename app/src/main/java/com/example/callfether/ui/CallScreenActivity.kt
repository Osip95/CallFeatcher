package com.example.callfether.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.callfether.PHONE_NUMBER_KEY
import com.example.callfether.R

class CallScreenActivity : AppCompatActivity() {
    lateinit var btnCallUp: Button
    lateinit var tvPhoneNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_screen)
        btnCallUp = findViewById(R.id.btnCallUp)
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber)
        tvPhoneNumber.text = getString(R.string.formatted_phone_number, intent.getStringExtra(PHONE_NUMBER_KEY))
        btnCallUp.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + tvPhoneNumber.text.toString())
            startActivity(intent)
        }
    }
}