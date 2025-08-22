package com.example.app_carwash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class PersonalActivity : AppCompatActivity() {

    lateinit var dni: EditText
    lateinit var nombre: EditText
    lateinit var direccion: EditText
    lateinit var movil: EditText
    lateinit var fechaIngreso: EditText
    lateinit var email: EditText
    lateinit var sueldo: EditText

    lateinit var btnAgregar: Button
    lateinit var btnEditar: Button
    lateinit var btnEliminar: Button
    lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)

        dni = findViewById(R.id.edtDni)
        nombre = findViewById(R.id.edtNombre)
        direccion = findViewById(R.id.edtDireccion)
        movil = findViewById(R.id.edtMovil)
        fechaIngreso = findViewById(R.id.edtFechaIngreso)
        email = findViewById(R.id.edtEmail)
        sueldo = findViewById(R.id.edtSueldo)

        btnAgregar = findViewById(R.id.btnAgregar)
        btnEditar = findViewById(R.id.btnEditar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnVolver = findViewById(R.id.btnRegresar)

        // BOTÓN AGREGAR
        btnAgregar.setOnClickListener {
            val json = JSONObject()
            json.put("dniPersona", dni.text.toString())
            json.put("nombre", nombre.text.toString())
            json.put("direccion", direccion.text.toString())
            json.put("movil", movil.text.toString())
            json.put("fechaIngreso", fechaIngreso.text.toString()) // yyyy-MM-dd
            json.put("email", email.text.toString())
            json.put("sueldo", sueldo.text.toString())

            sendRequest("/personal/crear", "POST", json, "Personal agregado correctamente")
        }

        // BOTÓN EDITAR
        btnEditar.setOnClickListener {
            val json = JSONObject()
            json.put("dniPersona", dni.text.toString())
            json.put("nombre", nombre.text.toString())
            json.put("direccion", direccion.text.toString())
            json.put("movil", movil.text.toString())
            json.put("fechaIngreso", fechaIngreso.text.toString())
            json.put("email", email.text.toString())
            json.put("sueldo", sueldo.text.toString())

            sendRequest("/personal", "PUT", json, "Personal actualizado correctamente")
        }

        // BOTÓN ELIMINAR
        btnEliminar.setOnClickListener {
            val json = JSONObject()
            json.put("dniPersona", dni.text.toString())

            sendRequest("/personal", "DELETE", json, "Personal eliminado correctamente")
        }

        // BOTÓN VOLVER
        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }

    // 🔹 AHORA SÍ, FUNCIÓN FUERA DEL onCreate
    private fun sendRequest(endpoint: String, method: String, json: JSONObject, successMessage: String) {
        Thread {
            try {
                val url = URL(Conexion.getUrl(endpoint))
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = method
                conn.setRequestProperty("Content-Type", "application/json; utf-8")
                conn.doOutput = true

                // Enviar JSON
                val writer = OutputStreamWriter(conn.outputStream)
                writer.write(json.toString())
                writer.flush()

                val responseCode = conn.responseCode
                val response = BufferedReader(InputStreamReader(conn.inputStream)).readText()

                runOnUiThread {
                    if (responseCode == 200) {
                        Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error: $response", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }
}
