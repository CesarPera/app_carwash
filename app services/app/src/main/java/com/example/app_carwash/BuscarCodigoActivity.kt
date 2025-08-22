package com.example.app_carwash

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class BuscarCodigoActivity : AppCompatActivity() {

    lateinit var etCodigo: EditText
    lateinit var btnBuscar: Button
    lateinit var btnRegresar: Button
    lateinit var tbProductos: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_codigo)

        etCodigo = findViewById(R.id.etCodigo)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnRegresar = findViewById(R.id.btnregresar)
        tbProductos = findViewById(R.id.tbproductosBuscar)  

        btnBuscar.setOnClickListener {
            val codigo = etCodigo.text.toString().trim()
            if (codigo.isEmpty()) {
                Toast.makeText(this, "Ingrese un código", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            buscarProductoBackend(codigo)
        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, AgregarProductoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun buscarProductoBackend(codigo: String) {
        val json = JSONObject().apply { put("idservicio", codigo) }

        Thread {
            try {
                val url = URL(Conexion.getUrl("/servicios/buscar"))
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json; utf-8")
                conn.doOutput = true

                OutputStreamWriter(conn.outputStream).use { it.write(json.toString()) }

                val responseCode = conn.responseCode
                val responseText = BufferedReader(InputStreamReader(conn.inputStream)).use { it.readText() }

                runOnUiThread {
                    if (responseCode in 200..299) {
                        tbProductos.removeAllViews() // Limpiar resultados anteriores
                        val jsonResponse = JSONObject(responseText)
                        agregarFilaTabla(jsonResponse)
                    } else {
                        Toast.makeText(this, "No se encontró el servicio: $responseCode", Toast.LENGTH_SHORT).show()
                    }
                }

                conn.disconnect()
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error de conexión: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    private fun agregarFilaTabla(json: JSONObject) {
        val fila = LayoutInflater.from(this).inflate(R.layout.listar_layout, null, false)
        val tvCodigo = fila.findViewById<TextView>(R.id.tvCodigo)
        val tvDescripcion = fila.findViewById<TextView>(R.id.tvDescripcion)
        val tvPrecio = fila.findViewById<TextView>(R.id.tvPrecio)

        tvCodigo.text = json.optString("idservicio")
        tvDescripcion.text = json.optString("descripcion")
        tvPrecio.text = json.optDouble("costo").toString()

        fila.setOnClickListener {
            resetColorFilas()
            fila.setBackgroundColor(Color.GRAY)
        }

        tbProductos.addView(fila)
    }

    private fun resetColorFilas() {
        for (i in 0 until tbProductos.childCount) {
            tbProductos.getChildAt(i).setBackgroundColor(Color.WHITE)
        }
    }
}