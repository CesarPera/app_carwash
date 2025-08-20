package com.example.app_carwash

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class AgregarProductoActivity : AppCompatActivity() {
    private lateinit var edtCodigo: EditText
    private lateinit var edtNombre: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)

        edtCodigo = findViewById(R.id.edtCodigo)
        edtNombre = findViewById(R.id.edtNombre)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)

        btnGuardar.setOnClickListener {
            val codigo = edtCodigo.text.toString()
            val nombre = edtNombre.text.toString()

            if (codigo.isNotEmpty() && nombre.isNotEmpty()) {
                val data = intent
                data.putExtra("codigo", codigo)
                data.putExtra("nombre", nombre)
                setResult(Activity.RESULT_OK, data)
                finish()
            } else {
                edtCodigo.error = "Ingrese el código"
                edtNombre.error = "Ingrese el nombre"
            }
        }

        btnCancelar.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}