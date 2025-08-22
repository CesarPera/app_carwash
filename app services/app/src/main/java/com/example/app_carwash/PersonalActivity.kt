package com.example.app_carwash

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class PersonalActivity : AppCompatActivity() {

    private lateinit var edtDni: EditText
    private lateinit var edtNombre: EditText
    private lateinit var edtDireccion: EditText
    private lateinit var edtMovil: EditText
    private lateinit var edtFechaIngreso: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtSueldo: EditText
    private lateinit var tbPersonal: TableLayout

    private val urlBase = Conexion.getUrl("/personal")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)

        // Inicializar campos
        edtDni = findViewById(R.id.edtDni)
        edtNombre = findViewById(R.id.edtNombre)
        edtDireccion = findViewById(R.id.edtDireccion)
        edtMovil = findViewById(R.id.edtMovil)
        edtFechaIngreso = findViewById(R.id.edtFechaIngreso)
        edtEmail = findViewById(R.id.edtEmail)
        edtSueldo = findViewById(R.id.edtSueldo)
        tbPersonal = findViewById(R.id.tbproductos)

        // Botones
        findViewById<Button>(R.id.btnAgregar).setOnClickListener { crearPersonal() }
        findViewById<Button>(R.id.btnEditar).setOnClickListener { actualizarPersonal() }
        findViewById<Button>(R.id.btnEliminar).setOnClickListener { eliminarPersonal() }
        findViewById<Button>(R.id.btnRegresar).setOnClickListener { finish() }

        listarPersonal()
    }

    // ================== 🔹 Métodos CRUD ==================
    private fun crearPersonal() {
        val json = getJsonPersonal()
        ejecutarPeticion("POST", urlBase, json) {
            runOnUiThread {
                Toast.makeText(this, "Personal agregado", Toast.LENGTH_LONG).show()
                limpiarCampos()
                listarPersonal()
            }
        }
    }

    private fun actualizarPersonal() {
        val json = getJsonPersonal()
        ejecutarPeticion("PUT", urlBase, json) {
            runOnUiThread {
                Toast.makeText(this, "Personal actualizado", Toast.LENGTH_LONG).show()
                limpiarCampos()
                listarPersonal()
            }
        }
    }

    private fun eliminarPersonal() {
        val json = JSONObject()
        json.put("dniPersona", edtDni.text.toString())
        ejecutarPeticion("DELETE", urlBase, json) {
            runOnUiThread {
                Toast.makeText(this, "Personal eliminado", Toast.LENGTH_LONG).show()
                limpiarCampos()
                listarPersonal()
            }
        }
    }

    private fun listarPersonal() {
        Thread {
            try {
                val url = URL(urlBase)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val array = JSONArray(response)

                runOnUiThread {
                    tbPersonal.removeAllViews()
                    for (i in 0 until array.length()) {
                        val personal = array.getJSONObject(i)
                        val row = TableRow(this)

                        val dni = TextView(this)
                        dni.text = personal.getString("dniPersona")
                        val nombre = TextView(this)
                        nombre.text = personal.getString("nombre")
                        val sueldo = TextView(this)
                        sueldo.text = personal.getString("sueldo")

                        row.addView(dni)
                        row.addView(nombre)
                        row.addView(sueldo)

                        // 🔹 Al hacer click en la fila, llenar los EditText
                        row.setOnClickListener {
                            edtDni.setText(personal.getString("dniPersona"))
                            edtNombre.setText(personal.getString("nombre"))
                            edtDireccion.setText(personal.getString("direccion"))
                            edtMovil.setText(personal.getString("movil"))
                            edtFechaIngreso.setText(personal.getString("fechaIngreso"))
                            edtEmail.setText(personal.getString("email"))
                            edtSueldo.setText(personal.getString("sueldo"))
                        }

                        tbPersonal.addView(row)
                    }
                }
                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Error al listar: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    // ================== 🔹 Helpers ==================
    private fun getJsonPersonal(): JSONObject {
        val json = JSONObject()
        json.put("dniPersona", edtDni.text.toString())
        json.put("nombre", edtNombre.text.toString())
        json.put("direccion", edtDireccion.text.toString())
        json.put("movil", edtMovil.text.toString())

        // 🔹 Validar y formatear fecha
        val fechaTexto = edtFechaIngreso.text.toString().trim()
        val fechaFormateada = if (fechaTexto.isNotEmpty()) {
            try {
                val entrada = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
                val salida = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                salida.format(entrada.parse(fechaTexto)!!)
            } catch (e: Exception) {
                fechaTexto // si ya está en yyyy-MM-dd lo deja igual
            }
        } else {
            "2000-01-01" // valor por defecto
        }
        json.put("fechaIngreso", fechaFormateada)

        json.put("email", edtEmail.text.toString())

        // 🔹 Solo una vez sueldo, siempre número
        val sueldoTexto = edtSueldo.text.toString().trim()
        json.put("sueldo", if (sueldoTexto.isNotEmpty()) sueldoTexto else "0")

        return json
    }



    private fun ejecutarPeticion(method: String, urlStr: String, json: JSONObject, callback: (String) -> Unit) {
        Thread {
            try {
                val url = URL(urlStr)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = method
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                connection.outputStream.use { os ->
                    val input = json.toString().toByteArray(Charsets.UTF_8)
                    os.write(input, 0, input.size)
                }

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                callback(response)
                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    private fun limpiarCampos() {
        edtDni.text.clear()
        edtNombre.text.clear()
        edtDireccion.text.clear()
        edtMovil.text.clear()
        edtFechaIngreso.text.clear()
        edtEmail.text.clear()
        edtSueldo.text.clear()
    }
}
