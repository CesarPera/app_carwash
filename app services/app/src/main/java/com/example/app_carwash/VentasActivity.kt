package com.example.app_carwash

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import android.widget.Button
import android.widget.EditText

class VentasActivity : AppCompatActivity() {
    private lateinit var edtFecha: EditText
    private lateinit var edtDocumento: EditText
    private lateinit var edtCosto: EditText
    private lateinit var edtIdServicio: EditText
    private lateinit var edtPlaca: EditText
    private lateinit var edtDni: EditText
    private lateinit var edtObservacion: EditText
    private lateinit var tbProductos: TableLayout
    private lateinit var btnAgregar: Button
    private lateinit var btnEditar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnRegresar: Button

    private val baseUrl = "http://localhost:8080/ventas" //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventas)


        // Inicializar vistas
        edtFecha = findViewById(R.id.edtFecha)
        edtDocumento = findViewById(R.id.edtDocumento)
        edtCosto = findViewById(R.id.edtCosto)
        edtIdServicio = findViewById(R.id.edtIdServicio)
        edtPlaca = findViewById(R.id.edtPlaca)
        edtDni = findViewById(R.id.edtDni)
        edtObservacion = findViewById(R.id.edtObservacion)
        tbProductos = findViewById(R.id.tbproductos)
        btnAgregar = findViewById(R.id.btnAgregar)
        btnEditar = findViewById(R.id.btnEditar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnRegresar = findViewById(R.id.btnRegresar)

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


        // Eventos
        btnAgregar.setOnClickListener { agregarVenta() }
        btnEditar.setOnClickListener { editarVenta() }
        btnEliminar.setOnClickListener { eliminarVenta() }
        btnRegresar.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }

        listarVentas() // Listar al iniciar
    }


    private fun listarVentas() {
        Thread {
            try {
                val url = URL("http://localhost:8080/ventas")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connect()

                val responseCode = conn.responseCode
                val response = BufferedReader(InputStreamReader(conn.inputStream)).readText()

                runOnUiThread {
                    if (responseCode == 200) {
                        val jsonArray = JSONArray(response)
                        tbProductos.removeAllViews()

                        // Encabezados
                        val header = TableRow(this)
                        listOf("ID", "Documento", "Costo", "Servicio", "Placa", "DNI").forEach { texto ->
                            val tv = TextView(this)
                            tv.text = texto
                            tv.setPadding(8,8,8,8)
                            header.addView(tv)
                        }
                        tbProductos.addView(header)

                        // Filas
                        for (i in 0 until jsonArray.length()) {
                            val venta = jsonArray.getJSONObject(i)
                            val row = TableRow(this)
                            listOf(
                                venta.getString("idservicioOtorgado"),
                                venta.getString("documento"),
                                venta.getString("costo"),
                                venta.getString("idservicio"),
                                venta.getString("placa"),
                                venta.getString("dniPersona")
                            ).forEach { texto ->
                                val tv = TextView(this)
                                tv.text = texto
                                tv.setPadding(8,8,8,8)
                                row.addView(tv)
                            }
                            tbProductos.addView(row)
                        }
                    } else {
                        Toast.makeText(this, "Error al listar ventas", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    private fun agregarVenta() {
        val body = crearJsonBody()
        if (body == null) return

        Thread {
            try {
                val url = URL("http://localhost:8080/ventas")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json; utf-8")
                conn.doOutput = true

                OutputStreamWriter(conn.outputStream).use { it.write(body.toString()) }

                val responseCode = conn.responseCode
                val response = BufferedReader(InputStreamReader(conn.inputStream)).readText()

                runOnUiThread {
                    if (responseCode == 200) {
                        Toast.makeText(this, "Venta agregada", Toast.LENGTH_SHORT).show()
                        limpiarCampos()
                        listarVentas()
                    } else {
                        Toast.makeText(this, "Error al agregar venta", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    private fun editarVenta() {
        val body = crearJsonBody()
        if (body == null) return

        Thread {
            try {
                val url = URL("http://localhost:8080/ventas")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "PUT"
                conn.setRequestProperty("Content-Type", "application/json; utf-8")
                conn.doOutput = true

                OutputStreamWriter(conn.outputStream).use { it.write(body.toString()) }

                val responseCode = conn.responseCode
                val response = BufferedReader(InputStreamReader(conn.inputStream)).readText()

                runOnUiThread {
                    if (responseCode == 200) {
                        Toast.makeText(this, "Venta editada", Toast.LENGTH_SHORT).show()
                        limpiarCampos()
                        listarVentas()
                    } else {
                        Toast.makeText(this, "Error al editar venta", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    private fun eliminarVenta() {
        val id = edtIdServicio.text.toString()
        if (id.isBlank()) {
            Toast.makeText(this, "Ingrese ID del servicio a eliminar", Toast.LENGTH_SHORT).show()
            return
        }

        Thread {
            try {
                val url = URL("http://localhost:8080/ventas")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "DELETE"
                conn.setRequestProperty("Content-Type", "application/json; utf-8")
                conn.doOutput = true

                val body = JSONObject()
                body.put("idservicio_otorgado", id)

                OutputStreamWriter(conn.outputStream).use { it.write(body.toString()) }

                val responseCode = conn.responseCode
                val response = BufferedReader(InputStreamReader(conn.inputStream)).readText()

                runOnUiThread {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                    listarVentas()
                }

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    private fun crearJsonBody(): JSONObject? {
        val idServicio = edtIdServicio.text.toString()
        val fecha = edtFecha.text.toString()
        val documento = edtDocumento.text.toString()
        val costo = edtCosto.text.toString()
        val placa = edtPlaca.text.toString()
        val dni = edtDni.text.toString()
        val observacion = edtObservacion.text.toString()

        if (idServicio.isBlank() || fecha.isBlank() || documento.isBlank() || costo.isBlank()) {
            runOnUiThread {
                Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
            }
            return null
        }

        val body = JSONObject()
        body.put("idservicio_otorgado", idServicio)
        body.put("fecha", fecha)
        body.put("documento", documento)
        body.put("costo", costo.toFloat())
        body.put("idservicio", idServicio)
        body.put("placa", placa)
        body.put("dni_persona", dni)
        body.put("observacion", observacion)
        return body
    }

    private fun limpiarCampos() {
        edtFecha.text.clear()
        edtDocumento.text.clear()
        edtCosto.text.clear()
        edtIdServicio.text.clear()
        edtPlaca.text.clear()
        edtDni.text.clear()
        edtObservacion.text.clear()
    }
}



