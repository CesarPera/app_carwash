package com.example.app_carwash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class VisionActivity : AppCompatActivity() {

    lateinit var btnvolver2 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vision2)

        btnvolver2 = findViewById(R.id.btnvolver2)

        btnvolver2.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
    }
}}