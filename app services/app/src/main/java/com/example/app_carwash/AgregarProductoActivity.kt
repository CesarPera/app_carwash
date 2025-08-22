package com.example.app_carwash

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import android.content.Intent
import org.json.JSONObject
import org.json.JSONArray
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader

class AgregarProductoActivity : AppCompatActivity() {

    var tbproductos: TableLayout? = null
    lateinit var et1: EditText
    lateinit var et2: EditText
    lateinit var et3: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)

        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        et3 = findViewById(R.id.et3)

        val btnCerrar = findViewById<Button>(R.id.btnregresar)
        val btnAgregar = findViewById<Button>(R.id.btnagregar)
        val btnEditar = findViewById<Button>(R.id.btneditar)
        val btnEliminar = findViewById<Button>(R.id.btneliminar)

        tbproductos = findViewById(R.id.tbproductos)
        tbproductos?.removeAllViews()

        // ✅ Listar productos al iniciar
        listarProductosBackend()

        // BOTÓN AGREGAR
        btnAgregar.setOnClickListener {
            val codigo = et1.text.toString()
            val descripcion = et2.text.toString()
            val precio = et3.text.toString()

            if (codigo.isNotEmpty() && descripcion.isNotEmpty() && precio.isNotEmpty()) {
                agregarProductoBackend(codigo, descripcion, precio.toFloat())
            } else {
                Toast.makeText(this, "Los campos deben tener texto", Toast.LENGTH_LONG).show()
            }
        }

        // BOTÓN EDITAR
        btnEditar.setOnClickListener {
            val codigo = et1.text.toString()
            val descripcion = et2.text.toString()
            val precio = et3.text.toString()
            if (codigo.isNotEmpty()) {
                actualizarProductoBackend(codigo, descripcion, precio.toFloat())
            } else {
                Toast.makeText(this, "Seleccione un producto para editar", Toast.LENGTH_SHORT).show()
            }
        }

        // BOTÓN ELIMINAR
        btnEliminar.setOnClickListener {
            val codigo = et1.text.toString()
            if (codigo.isNotEmpty()) {
                eliminarProductoBackend(codigo)
            } else {
                Toast.makeText(this, "Seleccione un producto para eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        // BOTÓN REGRESAR
        btnCerrar.setOnClickListener {
            cerrar()
        }
    }

    // 🔹 Listar todos los productos desde backend
    private fun listarProductosBackend() {
        Thread {
            try {
                val url = URL(Conexion.getUrl("/servicios"))
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.setRequestProperty("Content-Type", "application/json; utf-8")

                val responseCode = conn.responseCode
                val response = BufferedReader(InputStreamReader(conn.inputStream)).use { it.readText() }

                runOnUiThread {
                    if (responseCode in 200..299) {
                        val jsonArray = JSONArray(response)
                        tbproductos?.removeAllViews()
                        for (i in 0 until jsonArray.length()) {
                            val json = jsonArray.getJSONObject(i)
                            llenarTablaBackend(json)
                        }
                    } else {
                        Toast.makeText(this, "Error al listar productos", Toast.LENGTH_SHORT).show()
                    }
                }
                conn.disconnect()
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    // 🔹 Agregar producto al backend
    private fun agregarProductoBackend(id: String, descripcion: String, costo: Float) {
        val json = JSONObject()
        json.put("idservicio", id)
        json.put("descripcion", descripcion)
        json.put("costo", costo)

        Thread {
            try {
                val url = URL(Conexion.getUrl("/servicios"))
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json; utf-8")
                conn.doOutput = true

                OutputStreamWriter(conn.outputStream).use { it.write(json.toString()) }

                val responseCode = conn.responseCode

                runOnUiThread {
                    if (responseCode in 200..299) {
                        Toast.makeText(this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show()
                        limpiarCampos()
                        listarProductosBackend()
                    } else {
                        Toast.makeText(this, "Error al agregar producto", Toast.LENGTH_SHORT).show()
                    }
                }
                conn.disconnect()
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    // 🔹 Actualizar producto en backend
    private fun actualizarProductoBackend(id: String, descripcion: String, costo: Float) {
        val json = JSONObject()
        json.put("idservicio", id)
        json.put("descripcion", descripcion)
        json.put("costo", costo)

        Thread {
            try {
                val url = URL(Conexion.getUrl("/servicios/actualizar"))
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "PUT"
                conn.setRequestProperty("Content-Type", "application/json; utf-8")
                conn.doOutput = true

                OutputStreamWriter(conn.outputStream).use { it.write(json.toString()) }

                val responseCode = conn.responseCode

                runOnUiThread {
                    if (responseCode in 200..299) {
                        Toast.makeText(this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show()
                        limpiarCampos()
                        resetColorRegistros()
                        listarProductosBackend()
                    } else {
                        Toast.makeText(this, "Error al actualizar producto", Toast.LENGTH_SHORT).show()
                    }
                }
                conn.disconnect()
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    // 🔹 Eliminar producto en backend
    private fun eliminarProductoBackend(id: String) {
        val json = JSONObject()
        json.put("idservicio", id)

        Thread {
            try {
                val url = URL(Conexion.getUrl("/servicios/eliminar"))
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "DELETE"
                conn.setRequestProperty("Content-Type", "application/json; utf-8")
                conn.doOutput = true

                OutputStreamWriter(conn.outputStream).use { it.write(json.toString()) }

                val responseCode = conn.responseCode

                runOnUiThread {
                    if (responseCode in 200..299) {
                        Toast.makeText(this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show()
                        limpiarCampos()
                        resetColorRegistros()
                        listarProductosBackend()
                    } else {
                        Toast.makeText(this, "Error al eliminar producto", Toast.LENGTH_SHORT).show()
                    }
                }
                conn.disconnect()
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    // 🔹 Llenar tabla desde JSON
    private fun llenarTablaBackend(json: JSONObject) {
        val registro = LayoutInflater.from(this).inflate(R.layout.listar_layout, null, false)
        val tvCodigo = registro.findViewById<TextView>(R.id.tvCodigo)
        val tvDescripcion = registro.findViewById<TextView>(R.id.tvDescripcion)
        val tvPrecio = registro.findViewById<TextView>(R.id.tvPrecio)

        tvCodigo.text = json.optString("idservicio")
        tvDescripcion.text = json.optString("descripcion")
        tvPrecio.text = json.optDouble("costo").toString()

        registro.setOnClickListener { clickRegistroProducto(registro) }
        tbproductos?.addView(registro)
    }

    // 🔹 Seleccionar registro
    fun clickRegistroProducto(view: View) {
        resetColorRegistros()
        view.setBackgroundColor(Color.GRAY)

        val registro = view as TableRow
        val controlCodigo = registro.getChildAt(0) as TextView
        val controlNombre = registro.getChildAt(1) as TextView
        val controlPrecio = registro.getChildAt(2) as TextView

        et1.setText(controlCodigo.text.toString())
        et2.setText(controlNombre.text.toString())
        et3.setText(controlPrecio.text.toString())
    }

    // 🔹 Resetear colores de filas
    fun resetColorRegistros() {
        for (i in 0 until tbproductos!!.childCount) {
            tbproductos?.getChildAt(i)?.setBackgroundColor(Color.WHITE)
        }
    }

    // 🔹 Limpiar campos
    fun limpiarCampos() {
        et1.setText("")
        et2.setText("")
        et3.setText("")
    }

    // 🔹 Cerrar Activity
    fun cerrar() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Desea regresar a la página de inicio?")
            .setTitle("Regresar")
            .setPositiveButton(android.R.string.yes) { _, _ ->
                startActivity(Intent(this, MainActivity2::class.java))
                finish()
            }
            .setNegativeButton(android.R.string.no) { _, _ -> }
        builder.create().show()
    }
}