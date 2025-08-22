package com.example.app_carwash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btncontinuar: Button
    lateinit var btncerrar: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btncontinuar = findViewById(R.id.btncontinuar1)
        btncerrar = findViewById(R.id.btncerrar1)

        btncontinuar.setOnClickListener {
            val intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
            finish()
        }

        btncerrar.setOnClickListener {
            finish()
        }

    }
}

