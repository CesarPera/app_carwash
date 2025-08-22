package com.example.app_carwash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.content.Intent

class BuscarCodigoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_codigo)

        val etCodigo = findViewById<EditText>(R.id.etCodigo)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)
        val btnRegresar = findViewById<Button>(R.id.btnregresar)

        btnBuscar.setOnClickListener {
            val codigo = etCodigo.text.toString().trim()

            // 🔍 Ejemplo estático: luego aquí conectarás con tu BD
            when (codigo) {
                "S001" -> tvResultado.text = "Código: S001\nDescripción: Lavado básico\nCosto: 20.0"
                "S002" -> tvResultado.text = "Código: S002\nDescripción: Lavado Premium\nCosto: 35.0"
                else -> tvResultado.text = "❌ No se encontró el servicio con código $codigo"
            }
        }
        btnRegresar.setOnClickListener {
            val intent = Intent(this, AgregarProductoActivity::class.java)
            startActivity(intent)
            finish() // opcional: cierra esta actividad para que no quede en el stack
        }
    }
}