package com.example.app_carwash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class VentasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventas)

        lateinit var dni : EditText
        lateinit var nombre : EditText
        lateinit var direccion : EditText
        lateinit var movil : EditText
        lateinit var fechaIngreso : EditText
        lateinit var email : EditText
        lateinit var sueldo : EditText
        lateinit var btnGuardar : Button
        lateinit var btnVolver : Button

        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        btnRegresar.setOnClickListener {
            // Ir a MainActivity
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
    }

}}